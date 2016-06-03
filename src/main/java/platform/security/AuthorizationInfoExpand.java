package platform.security;

import java.util.Set;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import demo.entity.Menu;

public class AuthorizationInfoExpand extends SimpleAuthorizationInfo {

    private static final long serialVersionUID = 8303600349692521800L;

    // 用户的菜单信息
    private Set<Menu> menuAll;

    private Set<Menu> menuLeaf;

    public AuthorizationInfoExpand(Set<Menu> menu, Set<Menu> menuLeafx) {
        menuAll = menu;
        menuLeaf = menuLeafx;
    }

    public Set<Menu> getMenuAll() {
        return menuAll;
    }

    public Set<Menu> getMenuLeaf() {
        return menuLeaf;
    }

    public void setMenuLeaf(Set<Menu> menuLeaf) {
        this.menuLeaf = menuLeaf;
    }

}
