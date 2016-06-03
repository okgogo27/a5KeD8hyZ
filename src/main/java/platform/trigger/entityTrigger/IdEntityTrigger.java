package platform.trigger.entityTrigger;

import java.util.UUID;

import platform.demo.entity.IdEntity;

public class IdEntityTrigger extends SimpleEntityTrigger<IdEntity> {

    @Override
    public void prePersistEntity(IdEntity entity) {
         entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        // entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // entity.setUser("admin");
        System.out.println("idEntity");
    }

}
