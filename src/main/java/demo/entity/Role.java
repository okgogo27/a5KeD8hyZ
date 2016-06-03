package demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import platform.demo.entity.IdEntity;

@Entity
@Table(name = "role")
public class Role extends IdEntity {

    private static final long serialVersionUID = -235639809436747394L;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 关联权限
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SYS_ROLE_PERMISSION", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "PERMISSION_ID") })
    @Fetch(FetchMode.SUBSELECT)
    private List<Permission> permissionList = new ArrayList<Permission>();

    /**
     * 关联菜单
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SYS_ROLE_MENU", joinColumns = {
            @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "MENU_ID") })
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("treePath")
    private List<Menu> menuList = new ArrayList<Menu>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

}
