package platform.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;

public class JpaDaoFactory extends JpaRepositoryFactory {

    public JpaDaoFactory(EntityManager entityManager) {
        super(entityManager);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata,
            EntityManager entityManager) {
        Class<?> repositoryInterface = metadata.getRepositoryInterface();
        if (JpaDao.class.isAssignableFrom(repositoryInterface)) {
            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
            JpaDaoExpand<?, ?> repo = new JpaDaoExpand(entityInformation, entityManager);
            return repo;
        } else {
            return super.getTargetRepository(metadata, entityManager);
        }
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (JpaDao.class.isAssignableFrom(metadata.getRepositoryInterface())) {
            return JpaDaoExpand.class;
        } else {
            return super.getRepositoryBaseClass(metadata);
        }

    }

}
