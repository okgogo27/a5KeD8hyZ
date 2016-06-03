package platform.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaDao<T, TD extends Serializable> extends JpaRepository<T, TD>, JpaSpecificationExecutor<T> {

    public T findOne(PredicateManager predicateManager, Predicate predicate);

    public List<T> findAll(PredicateManager predicateManager, Predicate predicate);

    public List<T> findAll(PredicateManager predicateManager, Predicate predicate, Sort sort);

    public Page<T> findAll(PredicateManager predicateManager, Predicate predicate, Pageable pageable);

}
