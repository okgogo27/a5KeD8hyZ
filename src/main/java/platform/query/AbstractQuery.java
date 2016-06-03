package platform.query;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import platform.repository.Operators;
import platform.repository.Operators.Operator;
import platform.repository.PredicateFilter;
import platform.repository.PredicateManager;

public abstract class AbstractQuery<T> {

    private PredicateManager predicateManager;

    @SuppressWarnings("unchecked")
    public AbstractQuery() {
        Class<T> clazz = (Class<T>) getGenericClass(getClass(), 0);
        predicateManager = new PredicateManager(clazz);
    }

    public PredicateManager getPredicateManager() {
        return predicateManager;
    }

    public void setPredicateManager(PredicateManager predicateManager) {
        this.predicateManager = predicateManager;
    }

    public Predicate getPredicates() {
        List<Predicate> predicates = new ArrayList<Predicate>();
        List<Field> childrenFields = getAllDeclaredField(getClass());
        for (Field field : childrenFields) {
            if (field.isAnnotationPresent(PredicateFilter.class)) {
                try {
                    PredicateFilter predicateFilter = field.getAnnotation(PredicateFilter.class);
                    String property = predicateFilter.fieldName().isEmpty() ? field.getName()
                            : predicateFilter.fieldName();
                    Operator value = predicateFilter.value();
                    // 注解私有变量须设为true才能获取值
                    field.setAccessible(true);
                    predicates.add(Operators.toPredicate(predicateManager, value, property, field.get(this)));
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return predicateManager.getCriteriaBuilder().and(predicates.toArray(new Predicate[0]));
    }

    private Class<?> getGenericClass(Class<?> clazz, int index) {
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

    private List<Field> getAllDeclaredField(Class<?> clazz) {
        List<Field> getAllDeclaredField = new ArrayList<Field>();
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != AbstractQuery.class) {
            getAllDeclaredField.addAll(Arrays.asList(searchType.getDeclaredFields()));
            searchType = searchType.getSuperclass();
        }
        return getAllDeclaredField;
    }

}
