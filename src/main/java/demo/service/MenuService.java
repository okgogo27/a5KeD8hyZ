package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import demo.entity.Menu;
import demo.reposity.MenuReposity;

@Service
public class MenuService {

    @Autowired
    private MenuReposity menuReposity;

    public List<Menu> findAllMenu() {

        return menuReposity.findAll(new Sort("treePath"));

    }
}
