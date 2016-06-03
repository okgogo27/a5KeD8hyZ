package platform.repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

@NoRepositoryBean
public class JpaDaoExpand<T, TD extends Serializable> extends SimpleJpaRepository<T, TD> implements JpaDao<T, TD> {

    private EntityManager entityManager;

    public JpaDaoExpand(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public T findOne(PredicateManager predicateManager, Predicate predicate) {
        return getTypedQuery(predicateManager, predicate, null).getSingleResult();
    }

    public List<T> findAll(PredicateManager predicateManager, Predicate predicate) {
        return getTypedQuery(predicateManager, predicate, null).getResultList();
    }

    public List<T> findAll(PredicateManager predicateManager, Predicate predicate, Sort sort) {
        return getTypedQuery(predicateManager, predicate, sort).getResultList();
    }

    public Page<T> findAll(PredicateManager predicateManager, Predicate predicate, Pageable pageable) {
        if (pageable != null) {
            return readPage(getTypedQuery(predicateManager, predicate, pageable.getSort()), pageable, predicate);
        } else {
            return new PageImpl<T>(getTypedQuery(predicateManager, predicate, null).getResultList());
        }
    }

    private Page<T> readPage(TypedQuery<T> query, Pageable pageable, Predicate predicate) {

        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        Long total = executeCountQuery(getCountQuery(predicate));
        List<T> content = total > pageable.getOffset() ? query.getResultList() : Collections.<T> emptyList();

        return new PageImpl<T>(content, pageable, total);
    }

    private Long executeCountQuery(TypedQuery<Long> query) {

        Assert.notNull(query);

        List<Long> totals = query.getResultList();
        Long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

    private TypedQuery<Long> getCountQuery(Predicate predicate) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<T> root = query.from(getDomainClass());

        if (predicate != null) {
            query.where(predicate);
        }

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        return entityManager.createQuery(query);
    }

    @SuppressWarnings("unchecked")
    private TypedQuery<T> getTypedQuery(PredicateManager predicateManager, Predicate predicate, Sort sort) {
        CriteriaBuilder criteriaBuilder = predicateManager.getCriteriaBuilder();
        CriteriaQuery<T> query = (CriteriaQuery<T>) predicateManager.getCriteriaQuery();
        Root<T> root = (Root<T>) predicateManager.getRoot();
        if (predicate != null) {
            query.where(predicate);
        }

        query.select(root);

        if (sort != null) {
            query.orderBy(QueryUtils.toOrders(sort, root, criteriaBuilder));
        }
        return applyRepositoryMethodMetadata(entityManager.createQuery(query));
    }

    private TypedQuery<T> applyRepositoryMethodMetadata(TypedQuery<T> query) {
        CrudMethodMetadata metadata = getRepositoryMethodMetadata();

        if (metadata == null) {
            return query;
        }

        LockModeType type = metadata.getLockModeType();
        TypedQuery<T> toReturn = type == null ? query : query.setLockMode(type);

        applyQueryHints(toReturn);

        return toReturn;
    }

    private void applyQueryHints(Query query) {
        for (Entry<String, Object> hint : getQueryHints().entrySet()) {
            System.out.println(hint.getValue());
            query.setHint(hint.getKey(), hint.getValue());
        }
    }

}
