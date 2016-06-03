package platform.repository;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import platform.utils.PredicateUtils;

import javax.persistence.criteria.CriteriaBuilder.In;

public class PredicateManager {

    private CriteriaBuilder criteriaBuilder;

    private CriteriaQuery<?> criteriaQuery;

    private Root<?> root;

    private EntityManager entityManager;

    // 手动注入或注解注入均可，初始化时已放入系统内存
    // private ConversionService conversionService;

    public PredicateManager(Class<?> clazz) {
        // conversionService = PredicateUtils.getConversionService();
        entityManager = PredicateUtils.getEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(clazz);
        root = criteriaQuery.from(clazz);
    }

    // ���
    public Predicate equal(String property, Object object) {
        return criteriaBuilder.equal(getExpression(property), object);
    }

    // �������
    // public Predicate propertyEqual(String LeftProperty, String RightProperty)
    // {
    // return criteriaBuilder.equal(getExpression(LeftProperty),
    // getExpression(RightProperty));
    // }

    // ����
    public Predicate notEqual(String property, Object object) {
        return criteriaBuilder.notEqual(getExpression(property), object);
    }

    // ���Բ���
    // public Predicate propertyNotEqual(String LeftProperty, String
    // RightProperty) {
    // return criteriaBuilder.notEqual(getExpression(LeftProperty),
    // getExpression(RightProperty));
    // }

    // ����Ϊ��
    public Predicate isNull(String property) {
        return criteriaBuilder.isNull(getExpression(property));
    }

    // ���Բ�Ϊ��
    public Predicate isNotNull(String property) {
        return criteriaBuilder.isNotNull(getExpression(property));
    }

    // ȫƥ��%%
    @SuppressWarnings("unchecked")
    public Predicate likeAll(String property, Object object) {
        Expression<String> expression = (Expression<String>) getExpression(property);
        return criteriaBuilder.like(expression, "%" + expression + "%");
    }

    // ��ƥ��%
    @SuppressWarnings("unchecked")
    public Predicate likeLeft(String property, Object object) {
        Expression<String> expression = (Expression<String>) getExpression(property);
        return criteriaBuilder.like(expression, "%" + object);
    }

    // ��ƥ��
    @SuppressWarnings("unchecked")
    public Predicate likeRight(String property, Object object) {
        Expression<String> expression = (Expression<String>) getExpression(property);
        return criteriaBuilder.like(expression, object + "%");
    }

    // ����
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Predicate greaterThan(String property, Object object) {
        Expression<?> expression = getExpression(property);
        // ��ֵ
        if (object.getClass() == Number.class) {
            return criteriaBuilder.gt((Expression<? extends Number>) expression, (Expression<? extends Number>) object);
        } else {
            return criteriaBuilder.greaterThan((Expression<? extends Comparable>) expression, (Comparable<?>) object);
        }
    }

    // ���ڵ���
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Predicate greaterThanOrEqual(String property, Object object) {
        Expression<?> expression = getExpression(property);
        // ��ֵ
        if (object.getClass() == Number.class) {
            return criteriaBuilder.ge((Expression<? extends Number>) expression, (Expression<? extends Number>) object);
        } else {
            return criteriaBuilder.greaterThanOrEqualTo((Expression<? extends Comparable>) expression,
                    (Comparable<?>) object);
        }
    }

    // С��
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Predicate lessThan(String property, Object object) {
        Expression<?> expression = getExpression(property);
        // ��ֵ
        if (object.getClass() == Number.class) {
            return criteriaBuilder.lt((Expression<? extends Number>) expression, (Expression<? extends Number>) object);
        } else {
            return criteriaBuilder.lessThan((Expression<? extends Comparable>) expression, (Comparable<?>) object);
        }
    }

    // С�ڵ���
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Predicate lessThanOrEqual(String property, Object object) {
        Expression<?> expression = getExpression(property);
        // ��ֵ
        if (object.getClass() == Number.class) {
            return criteriaBuilder.le((Expression<? extends Number>) expression, (Expression<? extends Number>) object);
        } else {
            return criteriaBuilder.lessThanOrEqualTo((Expression<? extends Comparable>) expression,
                    (Comparable<?>) object);
        }
    }

    // in ֧�ֶ�String
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Predicate in(String property, Object object) {
        Expression<?> expression = getExpression(property);
        String[] values = null;
        if (object instanceof String) {
            values = ((String) object).split(",");
        } else if (object instanceof String[]) {
            values = (String[]) object;
        } else if (object instanceof Collection) {
            values = ((Collection<String>) object).toArray(new String[] {});
        }
        if (values != null) {
            if (values.length == 1) {
                return criteriaBuilder.equal(expression, values[0]);
            } else {
                In in = criteriaBuilder.in(expression);
                for (String string : values) {
                    in.value(string);
                }
                return in;
            }
        } else {
            throw new IllegalArgumentException("criteria builder in sql error");
        }
    }

    private Expression<?> getExpression(String property) {
        return root.get(property);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public CriteriaQuery<?> getCriteriaQuery() {
        return criteriaQuery;
    }

    public void setCriteriaQuery(CriteriaQuery<?> criteriaQuery) {
        this.criteriaQuery = criteriaQuery;
    }

    public Root<?> getRoot() {
        return root;
    }

    public void setRoot(Root<?> root) {
        this.root = root;
    }

}
