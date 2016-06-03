package platform.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import platform.demo.entity.IdEntity;

public class SimpleCodeManager<T extends IdEntity> implements CodeMethod<T> {

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public SimpleCodeManager() {
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
    
    
    @Override
    public List<T> findCodeByType(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    

}
