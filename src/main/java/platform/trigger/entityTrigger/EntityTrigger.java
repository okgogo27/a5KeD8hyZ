package platform.trigger.entityTrigger;

public interface EntityTrigger<T> {

    Class<T> getActualTypeArgument();

    void prePersistEntity(T entity);

    void preUpdateEntity(T entity);

    void preRemoveEntity(T entity);

    void postPersistEntity(T entity);

    void postUpdateEntity(T entity);

    void postRemoveEntity(T entity);

}
