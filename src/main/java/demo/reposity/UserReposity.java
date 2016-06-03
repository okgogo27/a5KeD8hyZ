package demo.reposity;

import org.springframework.stereotype.Repository;

import demo.entity.User;
import platform.repository.JpaDao;

@Repository
public interface UserReposity extends JpaDao<User, String> {

    public User findByUserName(String name);

}
