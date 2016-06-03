package demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.entity.DemoEntity;
import demo.query.DemoQuery;
import demo.service.DemoService;
import platform.repository.SqlReposity;

@Controller
@RequestMapping("main")
public class DemoController {

    // private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DemoService demoService;

    // @Autowired
    // private MemcachedCacheManager memcachedCacheManager;

    // @Autowired
    // private MenuService menuService;

    // @Autowired
    // private SqlReposity sqlDao;

    @SuppressWarnings("unchecked")
    @RequestMapping("welcome")
    public String welcomeView(Model model) {

        DemoQuery demoQuery = new DemoQuery();
        //
        List<DemoEntity> list = demoService.find(demoQuery);

        String sql = "select * from demo where name like 'w%'";

        // List<DemoEntity> aa = (List<DemoEntity>)
        // sqlDao.createNativeQuery(sql);

        // List<MemcachedEntity> aa = (List<MemcachedEntity>)
        // PredicateUtils.getCodeManager()
        // .getCodeMethods(MemcachedEntity.class).findCodeByType("aa");

        // List<DemoEntity> sss = memcachedCacheManager.get("abc", "demo");
        //
        // if (sss == null) {
        // memcachedCacheManager.put("abc", "demo", list, 3600 * 4);
        // }

        // List<Menu> list = menuService.findAllMenu();
        // List<Menu> menuRoot = new ArrayList<Menu>();
        model.addAttribute("demoEntity", list);
        // logger.error("abc");

        // for (Menu menu : list) {
        // if (menu.getParentId() == null) {
        // Menu root = menu;
        // String parentId = root.getId();
        // for (Menu father : list) {
        // if (parentId.equals(father.getParentId())) {
        // root.getChildren().add(father);
        // Menu menuFather = father;
        // String fatherId = father.getId();
        // for (Menu child : list) {
        // if (fatherId.equals(child.getParentId())) {
        // menuFather.getChildren().add(child);
        // }
        // }
        // }
        // }
        // menuRoot.add(root);
        // }
        // }
        return "index";
    }

    @RequestMapping("save")
    public String save(DemoEntity demoEntity) {

        demoService.save(demoEntity);

        return "redirect:../main/welcome";
    }

    @RequestMapping("tile")
    public String tileDemo() {

        return "tile-demo";
    }

    @RequestMapping("tileX")
    public String tileaa() {

        return "tile-demoX";
    }

}
