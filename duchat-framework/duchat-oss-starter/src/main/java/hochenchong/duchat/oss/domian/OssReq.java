package hochenchong.duchat.oss.domian;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hochenchong
 * @date 2024/08/07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssReq {

    @Schema(description = "用户 id")
    private Long uid;
    @Schema(description = "文件存储路径")
    private String filePath;
    @Schema(description = "文件名")
    private String fileName;
    @Schema(description = "是否自动生成地址")
    private boolean autoPath;
}
