package demo.reposity;

import org.springframework.stereotype.Repository;

import demo.entity.Permission;
import platform.repository.JpaDao;

@Repository
public interface PermissionReposity extends JpaDao<Permission, String> {

}
