package platform.demo.entity;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity extends IdEntity {

    private static final long serialVersionUID = -4679991251833975319L;

    private Timestamp createTime;

    private Timestamp updateTime;

    private String user;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // @PrePersist
    // public void prePersist() {
    // BaseEntityTrigger.prePersistEntity(this);
    // }

}
