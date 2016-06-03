package demo.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import platform.demo.entity.BaseEntity;

@Entity
@Table(name = "demo")
public class DemoEntity extends BaseEntity {

    private static final long serialVersionUID = -1316824122390311493L;

    private String name;

    private String sex;

    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
