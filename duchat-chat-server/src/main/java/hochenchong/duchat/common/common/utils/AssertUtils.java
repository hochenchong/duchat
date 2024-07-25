package hochenchong.duchat.common.common.utils;

import cn.hutool.core.util.ObjectUtil;
import hochenchong.duchat.common.common.exception.CustomException;
import hochenchong.duchat.common.common.exception.ErrorEnum;

/**
 * 断言工具类
 *
 * @author hochenchong
 * @date 2024/07/25
 */
public class AssertUtils {

    /**
     * 对象必须为空
     *
     * @param obj 判断的对象
     * @param errorEnum 错误信息
     */
    public static void isEmpty(Object obj, ErrorEnum errorEnum) {
        if (!isEmpty(obj)) {
            throwException(errorEnum);
        }
    }

    /**
     * 对象不能为空
     *
     * @param obj 判断的对象
     * @param errorEnum 错误信息
     */
    public static void isNotEmpty(Object obj, ErrorEnum errorEnum) {
        if (isEmpty(obj)) {
            throwException(errorEnum);
        }
    }

    /**
     * 两个对象必须相同
     *
     * @param o1 对象1
     * @param o2 对象2
     * @param errorEnum 错误信息
     */
    public static void equal(Object o1, Object o2, ErrorEnum errorEnum) {
        if (!ObjectUtil.equal(o1, o2)) {
            throwException(errorEnum);
        }
    }

    /**
     * 两个对象必须不相同
     *
     * @param o1 对象1
     * @param o2 对象2
     * @param errorEnum 错误信息
     */
    public static void notEqual(Object o1, Object o2, ErrorEnum errorEnum) {
        if (ObjectUtil.equal(o1, o2)) {
            throwException(errorEnum);
        }
    }

    private static boolean isEmpty(Object obj) {
        return ObjectUtil.isEmpty(obj);
    }

    private static void throwException(ErrorEnum errorEnum) {
        throw new CustomException(errorEnum);
    }
}
