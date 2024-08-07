package hochenchong.duchat.common.chat.domain.entity.msg;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 视频类型消息
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "视频类型消息")
public class VideoMsgDTO extends BaseFileDTO {
    @Schema(description = "视频时长，单位：秒")
    @NotNull
    private Integer videoSecond;

    @Schema(description = "视频格式，例如 mp4")
    @NotBlank
    private String thumbUrl;

    @Schema(description = "缩略图大小，单位：字节")
    @NotNull
    private Integer thumbSize;

    @Schema(description = "缩略图宽度，单位为像素")
    @NotNull
    private Integer thumbWidth;

    @Schema(description = "缩略图高度，单位为像素")
    @NotNull
    private Long thumbHeight;
}
