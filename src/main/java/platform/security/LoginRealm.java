package platform.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.entity.Menu;
import demo.entity.Role;
import demo.entity.User;
import demo.service.UserService;

@Component
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    private Set<Menu> menuLeaf = new TreeSet<Menu>(Menu.comparator);

    private static User currentUser;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {

        List<Menu> visibleMenu = new ArrayList<Menu>();

        for (Role role : LoginRealm.currentUser.getRoleList()) {
            for (Menu menu : role.getMenuList()) {
                if (!visibleMenu.contains(menu)) {
                    visibleMenu.add(menu);
                }
            }
        }

        AuthorizationInfoExpand info = new AuthorizationInfoExpand(createMenu(visibleMenu), getMenuLeaf());

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) arg0;
        User user = userService.findbyUserName(token.getUsername());
        if (user != null && user.getUserName().equals(token.getUsername())
                && user.getPassWord().equals(String.valueOf(token.getPassword()))) {
            LoginRealm.currentUser = user;
            return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
        } else {
            return null;
        }
    }

    /**
     * 开放获取SimpleAuthorizationInfo
     */
    public AuthorizationInfoExpand getSimpleAuthorizationInfo(PrincipalCollection principals) {
        return (AuthorizationInfoExpand) getAuthorizationInfo(principals);
    }

    public Set<Menu> getMenuLeaf() {
        return menuLeaf;
    }

    // 组装菜单
    private Set<Menu> createMenu(List<Menu> menuList) {
        Set<Menu> menuAll = new TreeSet<Menu>(Menu.comparator);
        for (int i = 0; i < menuList.size(); i++) {
            if (menuList.get(i).getParentId() == null) {
                Menu root = menuList.get(i);
                String parentId = root.getId();
                for (int j = i + 1; j < menuList.size(); j++) {
                    if (parentId.equals(menuList.get(j).getParentId())) {
                        Menu menuFather = menuList.get(j);
                        root.getChildren().add(menuFather);
                        String fatherId = menuFather.getId();
                        for (int z = j + 1; z < menuList.size(); z++) {
                            if (fatherId.equals(menuList.get(z).getParentId())) {
                                Menu thirdMenu = menuList.get(z);
                                menuFather.getChildren().add(thirdMenu);
                                getMenuLeaf().add(thirdMenu);
                                j = z;
                            } else {
                                break;
                            }
                        }
                        i = j;
                    } else {
                        break;
                    }
                }
                menuAll.add(root);
            } else {
                break;
            }
        }
        return menuAll;
    }

}
