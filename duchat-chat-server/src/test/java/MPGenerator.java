import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.sql.Types;

/**
 * 适用版本：mybatis-plus-generator 3.5.1 及其以上版本，对历史版本不兼容！详情看：<a href="https://www.baomidou.com/pages/779a6e/#%E5%BF%AB%E9%80%9F%E5%85%A5%E9%97%A8">代码生成器（新）</a>
 * 具体的代码生成器配置：<a href="https://www.baomidou.com/pages/981406/">代码生成器配置新</a>
 * 例子里输出到目录设置在 src/test 下，避免直接覆盖掉正式代码
 * mapper/xml 里到 xml，拷贝到 main/resources/mapper 目录下
 *
 * @author hochenchong
 * @since 2024-03-19
 */
public class MPGenerator {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/duchat?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, "root", "mysql8.0")
                .globalConfig(builder -> {
                    builder.author("hochenchong") // 设置作者
                            // .enableSwagger() // swagger 1，2 使用这个
                            .enableSpringdoc() // swagger 3 使用这个
                            .outputDir(System.getProperty("user.dir") + "/duchat-chat-server/src/test/java") // 指定输出目录
                    ;
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    // 自定义类型转换
                    if (typeCode == Types.SMALLINT) {
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("hochenchong.duchat.common.user") // 设置父包名
                            .entity("domain.entity")
                            .mapper("mapper")
                            .controller("controller")
                            .serviceImpl("dao")
                            // .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir" + "/duchat-chat-server/src/main/resources")))
                    ;
                })
                // 策略配置
                // 模板配置参考：https://baomidou.com/reference/new-code-generator-configuration/#%E6%A8%A1%E6%9D%BF%E9%85%8D%E7%BD%AE-templateconfig
                .strategyConfig(builder -> {
                    // TODO 这里修改需要自动生成的表名，按 , 隔开
                    // include 与 exclude 只能配置一项 ，支持正则匹配、例如 ^t_.* 所有 t_ 开头的表名
                    builder.addInclude("^t_.*") // 设置需要生成的表名
                            .addTablePrefix("t_") // 设置过滤表前缀
                            // 设置 service 的模板，已经 service 的名称规则
                            .serviceBuilder().serviceTemplate("/templates/service.java").convertServiceFileName((entityName) -> entityName + "Service")
                            .serviceImplTemplate("/templates/serviceImpl.java")
                            .enableFileOverride() // 覆盖已生成文件
                            .formatServiceImplFileName("%sDao")
                            // 设置实体类信息
                            .entityBuilder()
                            .enableLombok() // 开启 Lombok
                            .naming(NamingStrategy.underline_to_camel) // 包、列的命名规则，使用驼峰规则
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .enableTableFieldAnnotation() // 字段和表注解
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                    ;
                })
                // .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}