package platform.utils;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import platform.trigger.entityTrigger.EntityTriggerManager;

//spring不支持注入静态变量，须注解在常量set方法
//静态类管理类
@Component
public final class PlatformUtils{
	
	private PlatformUtils(){}

	private static EntityManager entityManager;

	private final static ConversionService conversionService;

	private final static EntityTriggerManager entityTriggerManager;

	private final static CodeManager codeManager;
	
	static{
		conversionService = AplicationContextHelper.getBean(ConversionService.class);
		entityTriggerManager = AplicationContextHelper.getBean(EntityTriggerManager.class);
		codeManager = AplicationContextHelper.getBean(CodeManager.class);
	}

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

}
