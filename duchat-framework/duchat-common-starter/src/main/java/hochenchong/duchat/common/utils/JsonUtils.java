package hochenchong.duchat.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility class for JSON serialization and deserialization using Jackson ObjectMapper.
 */
public class JsonUtils {

    private static final ObjectMapper jsonMapper;

    static {
        jsonMapper = new ObjectMapper()
                // 反序列化时，如果遇到未知属性，不抛出异常，而是忽略
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 反序列化时，允许JSON字段名不带双引号的情况
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 自动查找并注册所有模块，例如JavaTimeModule
        jsonMapper.findAndRegisterModules();
        // 禁用日期作为时间戳的形式输出
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 创建 JavaTimeModule 模块，用于处理 Java 8 的日期时间 API 的序列化和反序列化
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyyMMdd")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyyMMdd")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        jsonMapper.registerModule(javaTimeModule);
    }

    /**
     * 将JSON字符串转换为指定类型的对象。
     *
     * @param str JSON字符串
     * @param clz 目标对象的Class
     * @param <T> 目标对象的类型
     * @return 解析后的对象
     * @throws UnsupportedOperationException 如果解析失败抛出异常
     */
    public static <T> T toObj(String str, Class<T> clz) {
        try {
            return jsonMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Failed to parse JSON string to object", e);
        }
    }

    /**
     * 将JSON字符串转换为复杂类型（如List<T>）的对象。
     *
     * @param str JSON字符串
     * @param clz 目标对象的TypeReference
     * @param <T> 目标对象的类型
     * @return 解析后的对象
     * @throws UnsupportedOperationException 如果解析失败抛出异常
     */
    public static <T> T toObj(String str, TypeReference<T> clz) {
        try {
            return jsonMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Failed to parse JSON string to object", e);
        }
    }

    /**
     * 将JSON字符串转换为List<T>类型的对象。
     *
     * @param str JSON字符串
     * @param clz List中元素的Class
     * @param <T> List中元素的类型
     * @return 解析后的List对象
     * @throws UnsupportedOperationException 如果解析失败抛出异常
     */
    public static <T> List<T> toList(String str, Class<T> clz) {
        try {
            return jsonMapper.readValue(str, TypeFactory.defaultInstance().constructCollectionType(List.class, clz));
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Failed to parse JSON string to List", e);
        }
    }

    /**
     * 将JSON字符串转换为JsonNode对象。
     *
     * @param str JSON字符串
     * @return JsonNode对象
     * @throws UnsupportedOperationException 如果解析失败抛出异常
     */
    public static JsonNode toJsonNode(String str) {
        try {
            return jsonMapper.readTree(str);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Failed to parse JSON string to JsonNode", e);
        }
    }

    /**
     * 将JsonNode对象转换为指定类型的对象。
     *
     * @param node JsonNode对象
     * @param clz 目标对象的Class
     * @param <T> 目标对象的类型
     * @return 转换后的对象
     * @throws UnsupportedOperationException 如果转换失败抛出异常
     */
    public static <T> T nodeToValue(JsonNode node, Class<T> clz) {
        try {
            return jsonMapper.treeToValue(node, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Failed to convert JsonNode to object", e);
        }
    }

    /**
     * 将对象转换为JSON字符串。
     *
     * @param t 待序列化的对象
     * @return 对象的JSON字符串表示
     * @throws UnsupportedOperationException 如果序列化失败抛出异常
     */
    public static String toStr(Object t) {
        try {
            return jsonMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Failed to serialize object to JSON string", e);
        }
    }
}