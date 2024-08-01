## 嘟嘟聊天室

### 项目介绍
嘟嘟聊天室，IM 项目

### 项目演示

### 技术选型
该项目尽可能使用新的版本，也顺带熟悉新版本的差异
* [Java 21](https://aws.amazon.com/cn/corretto)：选择自己喜欢的 OpenJDK 版本即可，这里选择了亚马逊家的 Corretto
* [SpringBoot 3](https://spring.io/projects/spring-boot)：Web 开发框架
* [MyBatis](https://blog.mybatis.org/)：ORM 框架，进行数据库操作
* [MyBatis Plus](https://baomidou.com/)
  * 简化对数据库的操作
  * 配置模板通过代码生成器生成自己需要的 Java 类，详情 [MPGenerator](duchat-chat-server/src/test/java/MPGenerator.java)
* [Swagger 3 + Knife4j 4.X 版本](https://doc.xiaominfo.com/)：接口文档以及美化，采用最新的版本，使用 OpenAPI 3
* [hutool](https://github.com/dromara/hutool)：一个功能丰富且易用的 Java 工具库。除了提供 java 命名空间的，还有提供 Jakarta 的，便于新版本 Java（如 Java 17）使用
* [ip2region](https://github.com/lionsoul2014/ip2region)：一个离线 IP 地址定位库和 IP 定位数据管理框架，10 微秒级别的查询效率

### 待办事项
* 单聊
* 群聊
* 屏蔽字
* 表情包
* 多类型消息支持
* 视频聊天
* 集成 AI 聊天