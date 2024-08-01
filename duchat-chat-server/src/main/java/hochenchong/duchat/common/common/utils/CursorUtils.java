package hochenchong.duchat.common.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hochenchong.duchat.common.common.domain.vo.request.CursorPageBaseReq;
import hochenchong.duchat.common.common.domain.vo.response.CursorPageBaseResp;
import hochenchong.duchat.common.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 游标分页工具类
 *
 * @author hochenchong
 * @date 2024/08/01
 */
public class CursorUtils {

    public static final String DATA_FORMAT_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public static <T> CursorPageBaseResp<Pair<T, Double>> getCursorPageByRedis(CursorPageBaseReq cursorPageBaseReq, String redisKey, Function<String, T> typeConvert) {
        Set<ZSetOperations.TypedTuple<String>> typedTuples;
        // 如果游标为空，则为从头开始取
        if (StringUtils.isBlank(cursorPageBaseReq.getCursor())) {
            typedTuples = RedisUtils.zReverseRangeWithScores(redisKey, cursorPageBaseReq.getPageSize());
        } else {
            typedTuples = RedisUtils.zReverseRangeByScoreWithScores(redisKey, Double.parseDouble(cursorPageBaseReq.getCursor()), cursorPageBaseReq.getPageSize());
        }
        // 为空则直接返回了
        if (CollectionUtils.isEmpty(typedTuples)) {
            return CursorPageBaseResp.empty();
        }
        List<Pair<T, Double>> result = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> t : typedTuples) {
            Pair<T, Double> tDoublePair = Pair.of(typeConvert.apply(t.getValue()), t.getScore());
            result.add(tDoublePair);
        }
        result.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        // 获取列表中最后一位的 value 值作为游标
        String cursor = result.getLast().getValue().toString();
        boolean isLast = result.size() != cursorPageBaseReq.getPageSize();
        return new CursorPageBaseResp<>(cursor, isLast, result);
    }

    public static <T> CursorPageBaseResp<T> getCursorPageByMysql(IService<T> mapper, CursorPageBaseReq request, Consumer<LambdaQueryWrapper<T>> initWrapper, SFunction<T, ?> cursorColumn) {
        // 游标字段类型
        Class<?> cursorType = LambdaUtils.getReturnType(cursorColumn);
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        // 额外条件，通过外部传入
        initWrapper.accept(wrapper);
        // 游标条件
        if (StringUtils.isNotBlank(request.getCursor())) {
            wrapper.lt(cursorColumn, parseCursor(request.getCursor(), cursorType));
        }
        // 游标方向
        wrapper.orderByDesc(cursorColumn);

        Page<T> page = mapper.page(request.plusPage(), wrapper);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return CursorPageBaseResp.empty();
        }
        // 取出游标
        String cursor = Optional.of(page.getRecords().getLast())
                .map(cursorColumn)
                .map(CursorUtils::toCursor)
                .orElse(null);
        // 判断是否最后一页
        boolean isLast = page.getRecords().size() != request.getPageSize();
        return new CursorPageBaseResp<>(cursor, isLast, page.getRecords());
    }

    private static String toCursor(Object o) {
        if (o instanceof Date) {
            return String.valueOf(((Date) o).getTime());
        }
        if (o instanceof LocalDateTime) {
            return ((LocalDateTime) o).format(DateTimeFormatter.ofPattern(DATA_FORMAT_PATTERN));
        }
        return o.toString();
    }

    private static Object parseCursor(String cursor, Class<?> cursorClass) {
        if (Date.class.isAssignableFrom(cursorClass)) {
            return new Date(Long.parseLong(cursor));
        }
        if (LocalDateTime.class.isAssignableFrom(cursorClass)) {
            return LocalDateTime.parse(cursor);
        }
        return cursor;
    }
}