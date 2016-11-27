package platform.utils;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import platform.trigger.entityTrigger.EntityTriggerManager;

//spring不支持注入静态变量，须注解在常量set方法
@Component
public final class PlatformUtils implements InitializingBean {

	private static EntityManager entityManager;

	private static ConversionService conversionService;

	private static EntityTriggerManager entityTriggerManager;

	private static CodeManager codeManager;

	public static EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		PlatformUtils.entityManager = entityManager;
	}

	public static ConversionService getConversionService() {
		return conversionService;
	}

	public static EntityTriggerManager getEntityTriggerManager() {
		return entityTriggerManager;
	}

	public static CodeManager getCodeManager() {
		return codeManager;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		PlatformUtils.conversionService = AplicationContextHelper.getBean(ConversionService.class);
		PlatformUtils.entityTriggerManager = AplicationContextHelper.getBean(EntityTriggerManager.class);
		PlatformUtils.codeManager = AplicationContextHelper.getBean(CodeManager.class);
	}

}
