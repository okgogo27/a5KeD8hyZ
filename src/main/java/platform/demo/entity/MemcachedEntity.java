package platform.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "memcached_entity")
public class MemcachedEntity extends IdEntity {

    private static final long serialVersionUID = 2581379023730758057L;

    private Integer memcached;

    public Integer getMemcached() {
        return memcached;
    }

    public void setMemcached(Integer memcached) {
        this.memcached = memcached;
    }

}
