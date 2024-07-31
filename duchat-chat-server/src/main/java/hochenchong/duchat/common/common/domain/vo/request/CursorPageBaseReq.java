package hochenchong.duchat.common.common.domain.vo.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "游标翻页请求")
public class CursorPageBaseReq {

    @Min(0)
    @Max(100)
    @Schema(description = "页面大小")
    private Integer pageSize = 10;

    @Schema(description = "游标")
    private String cursor;

    public Page plusPage() {
        return new Page(1, this.pageSize, false);
    }

    @JsonIgnore
    public boolean isFirstPage() {
        return StringUtils.isEmpty(cursor);
    }
}