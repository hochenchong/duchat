package hochenchong.duchat.common.common.utils;

import cn.hutool.core.util.ObjectUtil;
import hochenchong.duchat.common.common.exception.CommonErrorEnum;
import hochenchong.duchat.common.common.exception.CustomErrorEnum;
import hochenchong.duchat.common.common.exception.CustomException;
import hochenchong.duchat.common.common.exception.ErrorEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;

/**
 * 断言工具类
 *
 * @author hochenchong
 * @date 2024/07/25
 */
public class AssertUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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

    /**
     * 断言为 true
     *
     * @param expression 判断
     * @param errorEnum 错误信息
     */
    public static void isTrue(boolean expression, ErrorEnum errorEnum) {
        if (!expression) {
            throwException(errorEnum);
        }
    }

    /**
     * 断言为 false
     *
     * @param expression 判断
     * @param errorEnum 错误信息
     */
    public static void isFalse(boolean expression, ErrorEnum errorEnum) {
        if (expression) {
            throwException(errorEnum);
        }
    }

    public static <T> void checkValidateThrows(T body) {
        Set<ConstraintViolation<T>> violations = validator.validate(body);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                errorMsg.append(violation.getPropertyPath().toString()).append(":").append(violation.getMessage()).append(",");
            }
            throwException(CustomErrorEnum.PARAM_INVALID, errorMsg.substring(0, errorMsg.length() - 1));
        }
    }

    private static boolean isEmpty(Object obj) {
        return ObjectUtil.isEmpty(obj);
    }

    private static void throwException(ErrorEnum errorEnum) {
        throw new CustomException(errorEnum);
    }

    private static void throwException(ErrorEnum errorEnum, Object... arg) {
        if (Objects.isNull(errorEnum)) {
            errorEnum = CommonErrorEnum.SYSTEM_ERROR;
        }
        throw new CustomException(errorEnum.getErrorCode(), MessageFormat.format(errorEnum.getErrorMsg(), arg));
    }
}
