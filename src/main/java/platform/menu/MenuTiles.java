package platform.menu;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;

import demo.entity.Menu;
import platform.property.HttpRequestHelper;
import platform.security.AuthorizationInfoExpand;
import platform.security.LoginRealm;
import platform.utils.PlatformBeanHelper;

public class MenuTiles implements ViewPreparer {

    private static Set<Menu> menu = new TreeSet<Menu>();

    private static Set<Menu> menuLeaf = new TreeSet<Menu>();

    @Override
    public void execute(TilesRequestContext arg0, AttributeContext arg1) {

        if (menu.isEmpty()) {
            initMenu();
        }

        HttpServletRequest request = HttpRequestHelper.getRequest();
        String requestUrl = (String) request.getAttribute("javax.servlet.forward.request_uri");
        Set<Menu> secondMenu = new TreeSet<Menu>();
        for (Menu childMenu : menu) {
            if (requestUrl.equals(childMenu.getMenuUrl())) {
                secondMenu = childMenu.getChildren();
                break;
            }
        }
        if (secondMenu.isEmpty()) {
            secondMenu = menu.iterator().next().getChildren();
        }
        request.setAttribute("headMenu", menu);
        request.setAttribute("leftMenu", secondMenu);
        request.setAttribute("currentMenu", getCurrentMenu(requestUrl));
    }

    private void initMenu() {
        // 不推荐这种获取realm的方法
        // LoginRealm loginRealm = new LoginRealm();

        AuthorizationInfoExpand info = PlatformBeanHelper.getBeanByType(LoginRealm.class)
                .getSimpleAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());

        MenuTiles.menu = info.getMenuAll();
        MenuTiles.menuLeaf = info.getMenuLeaf();
    }

    private Menu getCurrentMenu(String url) {
        for (Menu menu : MenuTiles.menuLeaf) {
            if (url.equals(menu.getMenuUrl())) {
                return menu;
            }
        }
        return null;
    }

    public static Set<Menu> getMenu() {
        return menu;
    }

    public static Set<Menu> getMenuLeaf() {
        return menuLeaf;
    }

}
