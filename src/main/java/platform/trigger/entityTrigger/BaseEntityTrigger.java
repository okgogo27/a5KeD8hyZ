package platform.trigger.entityTrigger;

import java.sql.Timestamp;

import platform.demo.entity.BaseEntity;


public class BaseEntityTrigger extends SimpleEntityTrigger<BaseEntity> {

    @Override
    public void prePersistEntity(BaseEntity entity) {
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        System.out.println("base");

    }

    // public static void prePersistEntity(BaseEntity entity) {
    // entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    // entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
    // entity.setUser("admin");
    // }
    //
    // public static void preUpdateEntity(BaseEntity entity) {
    // entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    // }

}
