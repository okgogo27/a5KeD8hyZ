package platform.utils;

import java.util.List;

import platform.demo.entity.IdEntity;

public interface CodeMethod<T extends IdEntity> {
    
    public Class<T> getActualTypeArgument();

    public List<T> findCodeByType(String type);

}
