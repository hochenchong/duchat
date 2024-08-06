package hochenchong.duchat.common.chat.domain.entity.msg;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础文件 DTO
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "基础文件")
public class BaseFileDTO {

    @Schema(description = "数据大小，单位：字节")
    @NotNull
    private Long size;

    @Schema(description = "下载地址，可通过该 URL 地址直接下载相应文件")
    @NotBlank
    private String url;
}
