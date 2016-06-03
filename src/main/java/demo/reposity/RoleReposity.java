package demo.reposity;

import org.springframework.stereotype.Repository;

import demo.entity.Role;
import platform.repository.JpaDao;

@Repository
public interface RoleReposity extends JpaDao<Role, String>{

}
