package platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import platform.demo.entity.IdEntity;

/**
 * sql的查询接口
 */
public interface SqlReposity {

    public long count(String sql, Map<String, Object> filters);

    public List<? extends IdEntity> createQuery(String sql);

    public List<? extends IdEntity> createNativeQuery(String sql);

    public Page<Object[]> findPageForArray(String sql, Map<String, Object> filters, Pageable pageable);

    public Page<Map<String, Object>> findPageForMap(String sql, Map<String, Object> filters, Pageable pageable);

    public List<Object[]> findListForArray(String sql, Map<String, Object> filters, Pageable pageable);

    public List<Map<String, Object>> findListForMap(String sql, Map<String, Object> filters, Pageable pageable);

    public int executeUpdate(String sql, Map<String, Object> filters);

    public int executeProc(String procName, Map<String, Object> paramMap);

}
