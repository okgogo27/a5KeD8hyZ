package platform.trigger.entityTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import platform.demo.entity.IdEntity;

public class EntityTriggerManager {
	
	private EntityTriggerManager(){}

    private List<EntityTrigger<?>> entityTriggerAll;

    // 考虑并发问题，此处方法还有待选择
    private final Map<Class<?>, List<EntityTrigger<?>>> triggerMap = new ConcurrentHashMap<Class<?>, List<EntityTrigger<?>>>();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends IdEntity> void prePersist(T entity) {
        for (EntityTrigger<?> entityTrigger : getPartEntityList(entity.getClass())) {
            ((EntityTrigger) entityTrigger).prePersistEntity(entity);
        }
    }

    // 获取相关Trigger，并放入缓存
    private <T extends IdEntity> List<EntityTrigger<?>> getPartEntityList(Class<T> entityClazz) {
        List<EntityTrigger<?>> partEntityList = triggerMap.get(entityClazz);
        if (partEntityList == null) {
            partEntityList = new ArrayList<EntityTrigger<?>>();// TODO
            for (EntityTrigger<?> entityTrigger : entityTriggerAll) {
                Class<?> clazz = entityTrigger.getActualTypeArgument();
                if (clazz.isAssignableFrom(entityClazz)) {
                    partEntityList.add(entityTrigger);
                }
            }
            triggerMap.put(entityClazz, partEntityList);
        }
        return partEntityList;
    }

    public List<EntityTrigger<?>> getEntityTriggerAll() {
        return entityTriggerAll;
    }

    public void setEntityTriggerAll(List<EntityTrigger<?>> entityTriggerAll) {
        this.entityTriggerAll = entityTriggerAll;
    }

}
