package platform.utils.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import platform.security.LoginRealm;
import platform.utils.AplicationContextHelper;

/**
 * 获取spring bean，尽量采用使用IOC注入，必要时再使用该类。 spring
 * getBean底层实现代码存在同步代码(TODO：研究新版本是否还存在)，支持大并发时采用beanMap预先存好bean引用,具体项目以类似的写法实现
 */
public class PlatformBeanHelper {

    private static Map<Class<?>, Object> beanMap = null;

    // 具体项目修改这个即可
    private static Class<?>[] clazzs = { LoginRealm.class };

    private PlatformBeanHelper() {
    }

    /**
     * 通过类型获取相应唯一的Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBeanByType(Class<T> type) {
        if (beanMap == null) {
            initBeanMap();
        }
        Map<?, T> matchingBeans = (Map<?, T>) beanMap.get(type);
        if (matchingBeans.size() > 1) {
            throw new NoSuchBeanDefinitionException(type,
                    "expected single matching bean but found " + matchingBeans.size() + ": " + matchingBeans.keySet());
        }
        return (T) matchingBeans.values().toArray()[0];
    }

    /**
     * 通过类型获取一组bean
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<?, T> getBeansByType(Class<T> type) {
        if (beanMap == null) {
            initBeanMap();
        }
        return (Map<?, T>) beanMap.get(type);
    }

    private static synchronized void initBeanMap() {
        if (beanMap == null) {
            beanMap = new HashMap<Class<?>, Object>();
            for (Class<?> clazz : clazzs) {
                beanMap.put(clazz, getSpringBeansByType(clazz));
            }
        }
    }

    private static <T> Map<?, T> getSpringBeansByType(Class<T> type) {
        Map<?, T> matchingBeans = AplicationContextHelper.getApplicationContext().getBeansOfType(type);
        if (matchingBeans.isEmpty()) {
            throw new NoSuchBeanDefinitionException(type,
                    "Unsatisfied dependency of type [" + type + "]: expected at least 1 matching bean");
        }
        return matchingBeans;
    }
}
