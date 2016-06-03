package demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.entity.User;
import demo.reposity.UserReposity;

@Service
public class UserService {

    @Autowired
    UserReposity userReposity;

    public User findbyUserName(String name) {
        return userReposity.findByUserName(name);
    }

}
