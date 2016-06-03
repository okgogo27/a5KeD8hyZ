package platform.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import platform.trigger.entityTrigger.EntityTriggerManager;

@Component
public final class PredicateUtils {

    private static EntityManager entityManager;

    private static ConversionService conversionService;

    private static EntityTriggerManager entityTriggerManager;

    private static CodeManager codeManager;

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        PredicateUtils.entityManager = entityManager;
    }

    public static ConversionService getConversionService() {
        return conversionService;
    }

    // spring不支持注入静态变量，须注解在常量set方法
    @Autowired
    public void setConversionService(ConversionService conversionService) {
        PredicateUtils.conversionService = conversionService;
    }

    public static EntityTriggerManager getEntityTriggerManager() {
        return entityTriggerManager;
    }

    @Autowired
    public void setEntityTriggerManager(EntityTriggerManager entityTriggerManager) {
        PredicateUtils.entityTriggerManager = entityTriggerManager;
    }

    public static CodeManager getCodeManager() {
        return codeManager;
    }

    @Autowired
    public void setCodeManager(CodeManager codeManager) {
        PredicateUtils.codeManager = codeManager;
    }

}
