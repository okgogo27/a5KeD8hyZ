package demo.reposity;

import org.springframework.stereotype.Repository;

import demo.entity.Menu;
import platform.repository.JpaDao;

@Repository
public interface MenuReposity extends JpaDao<Menu, String> {

}
