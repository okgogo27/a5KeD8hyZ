package platform.utils.helper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ReflectionUtils extends org.springframework.util.ReflectionUtils {

	private ReflectionUtils() {
	}

	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取泛型参数类型
	 *
	 * @param type
	 *            类
	 * @param index
	 *            参数位置
	 * @return
	 */
	public static Class<?> getGenericClass(Class<?> clazz, int index) {
		Type type = clazz.getGenericSuperclass();

		while (!(type instanceof ParameterizedType)) {
			clazz = clazz.getSuperclass();
			type = clazz.getGenericSuperclass();
		}

		Type[] params = ((ParameterizedType) type).getActualTypeArguments();

		if ((params != null) && (params.length >= index - 1)) {
			return (Class<?>) params[index];
		}

		return null;
	}

	public static List<Field> getAllDeclaredField(Class<?> clazz) {
		List<Field> getAllDeclaredField = new ArrayList<Field>();
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			getAllDeclaredField.addAll(Arrays.asList(searchType.getDeclaredFields()));
			searchType = searchType.getSuperclass();
		}
		return getAllDeclaredField;
	}
}
