package platform.repository;

import javax.persistence.criteria.Predicate;

/**
 * ��ѯ��������
 */
public class Operators {

    /**
     * ��������ö��
     */
    public static enum Operator {

        /**
         * ���
         */
        EQUAL,

        /**
         * ����
         */
        GREATER_THAN,

        /**
         * ���ڵ���
         */
        GREATER_THAN_OR_EQUAL,

        /**
         * in����
         */
        IN,

        /**
         * in���ϻ���Ϊ��
         */
        IN_OR_NULL,

        /**
         * С��
         */
        LESS_THAN,

        /**
         * С�ڵ���
         */
        LESS_THAN_OR_EQUAL,

        /**
         * �м�ƥ��
         */
        LIKE_ALL,

        /**
         * ��ƥ��
         */
        LIKE_LEFT,

        /**
         * ��ƥ��
         */
        LIKE_RIGHT,

        /**
         * �����
         */
        NOT_EQUAL,

        /**
         * not in ����
         */
        NOT_IN,

        /**
         * not in ���ϻ���Ϊ��
         */
        NOT_IN_OR_NULL,

        /**
         * ��Ϊ��
         */
        NOT_NULL,

        /**
         * Ϊ��
         */
        NULL
    }

    /**
     * ����������תΪ��ѯ����Predicate
     *
     * @param builder
     *            wj builder
     * @param operator
     *            ��������
     * @param propertyName
     *            ��������
     * @param object
     *            ��ѯ�Ƚ�ֵ
     * @param joinType
     *            join����
     * @return
     */
    public static Predicate toPredicate(PredicateManager predicateManager, Operator operator, String property,
            Object object) {
        switch (operator) {
            case EQUAL:
                return predicateManager.equal(property, object);
            case LIKE_RIGHT:
                return predicateManager.likeRight(property, object);
            default:
                break;
        }
        return null;
    }
}
