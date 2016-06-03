package platform.demo.reposity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import platform.demo.entity.CodeEntity;


@Repository
public interface CodeReposity extends JpaRepository<CodeEntity, String> {

    public List<CodeEntity> findByType(String type);

}
