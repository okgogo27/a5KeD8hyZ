package demo.entity;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import platform.demo.entity.IdEntity;

@Entity
@Table(name = "menu")
public class Menu extends IdEntity {

    private static final long serialVersionUID = 5882390070729276617L;

    public static final Comparator<Menu> comparator = new Comparator<Menu>() {

        @Override
        public int compare(Menu m1, Menu m2) {
            return m1.getTreePath().compareTo(m2.getTreePath());
        }
    };

    private String parentId;

    private String menuName;

    private String menuUrl;

    private String treePath;

    /**
     * 下一级模块
     */
    @Transient
    private Set<Menu> children = new TreeSet<Menu>(Menu.comparator);

    private boolean visible = false;

    public Set<Menu> getChildren() {
        return children;
    }

    public void setChildren(Set<Menu> children) {
        this.children = children;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

}
