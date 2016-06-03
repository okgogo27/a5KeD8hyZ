package demo.reposity;

import org.springframework.stereotype.Repository;

import demo.entity.DemoEntity;
import platform.repository.JpaDao;

@Repository
public interface DemoReposity extends JpaDao<DemoEntity, String> {

}
