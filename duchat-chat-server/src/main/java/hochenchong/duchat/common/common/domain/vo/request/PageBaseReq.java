package hochenchong.duchat.common.common.domain.vo.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * @author hochenchong
 * @date 2024/07/31
 */
@Data
@Schema(description = "基础翻页请求")
public class PageBaseReq {
    @Schema(description = "页面大小")
    @Min(0)
    @Max(50)
    private int pageSize = 10;

    @Schema(description = "页面索引")
    private int pageNo = 1;

    /**
     * 使用 mybatis plus 的 page
     * @return page
     */
    public Page page() {
        return new Page(pageNo, pageSize);
    }
}
