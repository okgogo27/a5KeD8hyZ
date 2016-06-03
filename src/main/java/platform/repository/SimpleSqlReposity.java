package platform.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import platform.demo.entity.IdEntity;

@Component
public class SimpleSqlReposity implements SqlReposity {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends IdEntity> createQuery(String sql) {
        // TODO Auto-generated method stub
        return em.createQuery(sql).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends IdEntity> createNativeQuery(String sql) {
        // TODO Auto-generated method stub
        return em.createNativeQuery(sql).getResultList();
    }

    @Override
    public long count(String sql, Map<String, Object> filters) {
        Query query = createNativeQuery(sql, filters, null, -1);
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public Page<Object[]> findPageForArray(String sql, Map<String, Object> filters, Pageable pageable) {
        if (pageable == null) {
            return new PageImpl<Object[]>(findListForArray(sql, filters, pageable));
        } else {
            long total = count(getSimpleCountSql(sql), filters);

            List<Object[]> content = total > pageable.getOffset() ? findListForArray(sql, filters, pageable)
                    : Collections.<Object[]> emptyList();
            return new PageImpl<Object[]>(content, pageable, total);
        }
    }

    @Override
    public Page<Map<String, Object>> findPageForMap(String sql, Map<String, Object> filters, Pageable pageable) {
        if (pageable == null) {
            return new PageImpl<Map<String, Object>>(findListForMap(sql, filters, pageable));
        } else {
            long total = count(getSimpleCountSql(sql), filters);

            List<Map<String, Object>> content = total > pageable.getOffset() ? findListForMap(sql, filters, pageable)
                    : Collections.<Map<String, Object>> emptyList();
            return new PageImpl<Map<String, Object>>(content, pageable, total);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findListForArray(String sql, Map<String, Object> filters, Pageable pageable) {
        Query query = createNativeQuery(sql, filters, pageable, -1);
        // hibernate的Oracle分页时,非首页是采用rownum三层查询,会把rownum查出来,这里剔除rownum列
        List<Object[]> sqlResult = query.getResultList();
        if (pageable != null && pageable.getPageNumber() > 0) {
            List<Object[]> result = new ArrayList<Object[]>(sqlResult.size());
            for (Object[] objects : sqlResult) {
                Object[] objs = new Object[objects.length - 1];
                for (int i = 0; i < objects.length - 1; i++) {
                    objs[i] = objects[i];
                }
                result.add(objs);
            }
            return result;
        } else {
            return sqlResult;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findListForMap(String sql, Map<String, Object> filters, Pageable pageable) {
        Query query = createNativeQuery(sql, filters, pageable, -1);
        // 返回List的Map<String,Object>,可采用JPA实现Hibernate的特性 [返回的字段名Key为大写]
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        // hibernate的Oracle分页时,非首页是采用rownum三层查询,会把rownum查出来,这里剔除rownum列
        List<Map<String, Object>> sqlResult = query.getResultList();
        if (pageable.getPageNumber() > 0) {
            for (Map<String, Object> map : sqlResult) {
                map.remove("ROWNUM");
            }
            return sqlResult;
        } else {
            return sqlResult;
        }
    }

    @Override
    public int executeUpdate(String sql, Map<String, Object> filters) {
        return createNativeQuery(sql, filters, null, -1).executeUpdate();
    }

    @Override
    public int executeProc(String procName, Map<String, Object> paramMap) {
        // "{call proc_XXXX(:p)}"
        StringBuilder sb = new StringBuilder();
        sb.append("{call ");
        sb.append(procName);
        sb.append("(");

        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            sb.append(":").append(entry.getKey()).append(",");
        }
        if (paramMap.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")}");
        return executeUpdate(sb.toString(), paramMap);
    }

    private Query createNativeQuery(String sql, Map<String, Object> filters, Pageable pageable, int timeout) {
        Query query = em.createNativeQuery(sql);
        if (filters != null) {
            for (Entry<String, Object> filter : filters.entrySet()) {
                query.setParameter(filter.getKey(), filter.getValue());
            }
        }
        if (pageable != null) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        if (timeout > 0) {
            query.setHint("javax.persistence.query.timeout", timeout);
        }
        return query;
    }

    private String getSimpleCountSql(String sql) {
        // 简单获取求总数sql,要求规范sql
        String countSql = "select count(*) " + sql.substring(sql.indexOf(" from "));
        if (sql.indexOf(" order by ") >= 0) {
            countSql = "select count(*) " + sql.substring(sql.indexOf(" from "), sql.indexOf(" order by "));
        }
        return countSql;
    }

}
