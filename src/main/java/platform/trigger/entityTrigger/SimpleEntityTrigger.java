package platform.trigger.entityTrigger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SimpleEntityTrigger<T> implements EntityTrigger<T> {

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public SimpleEntityTrigger() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof Class) {
            type = ((Class<T>) type).getGenericSuperclass();
        }
        if (type instanceof ParameterizedType) {
            this.entityClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
    }

    public Class<T> getActualTypeArgument() {
        return entityClass;
    }

    public void prePersistEntity(T entity) {
        // TODO Auto-generated method stub

    }

    public void preUpdateEntity(T entity) {
        // TODO Auto-generated method stub

    }

    public void preRemoveEntity(T entity) {
        // TODO Auto-generated method stub

    }

    public void postPersistEntity(T entity) {
        // TODO Auto-generated method stub

    }

    public void postUpdateEntity(T entity) {
        // TODO Auto-generated method stub

    }

    public void postRemoveEntity(T entity) {
        // TODO Auto-generated method stub

    }

}
