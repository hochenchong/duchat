package hochenchong.duchat.common.common.config;
 
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import hochenchong.duchat.common.common.constant.DateConstants;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
@Configuration
public class LocalDateTimeConfig {

    /**
     * LocalDateTime 序列化器
     * @return 序列化器
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateConstants.DATA_TIME_FORMAT_PATTERN));
    }

    /**
     * LocalDateTime 反序列化器
     *
     * @return 反序列化器
     */
    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateConstants.DATA_TIME_FORMAT_PATTERN));
    }

    /**
     * 配置转换
     *
     * @return 配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
        };
    }
 
}