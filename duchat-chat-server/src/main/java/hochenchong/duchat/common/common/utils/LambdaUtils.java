package hochenchong.duchat.common.common.utils;

import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * Lambda 工具类
 *
 * @author hochenchong
 * @date 2024/08/01
 */
public class LambdaUtils {

    public static <T> Class<?> getReturnType(SFunction<T, ?> func) {
        LambdaMeta extract = com.baomidou.mybatisplus.core.toolkit.LambdaUtils.extract(func);
        return extract.getInstantiatedClass();
    }
}
