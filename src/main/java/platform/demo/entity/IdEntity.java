package platform.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import platform.utils.PlatformUtils;

@MappedSuperclass
public class IdEntity implements Serializable {

    private static final long serialVersionUID = -3552158079899090311L;

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        PlatformUtils.getEntityTriggerManager().prePersist(this);
    }

}
