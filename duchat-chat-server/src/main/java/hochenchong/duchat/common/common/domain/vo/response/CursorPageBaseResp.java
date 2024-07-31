package hochenchong.duchat.common.common.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "游标翻页返回")
public class CursorPageBaseResp<T> {

    @Schema(description = "游标")
    private String cursor;

    @Schema(description = "是否最后一页")
    private boolean last = false;

    @Schema(description = "数据列表")
    private List<T> list;

    public static <T> CursorPageBaseResp<T> init(CursorPageBaseResp<T> cursorPage, List<T> list) {
        CursorPageBaseResp<T> cursorPageBaseResp = new CursorPageBaseResp<T>();
        cursorPageBaseResp.setLast(cursorPage.isLast());
        cursorPageBaseResp.setList(list);
        cursorPageBaseResp.setCursor(cursorPage.getCursor());
        return cursorPageBaseResp;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(list);
    }

    public static <T> CursorPageBaseResp<T> empty() {
        CursorPageBaseResp<T> cursorPageBaseResp = new CursorPageBaseResp<T>();
        cursorPageBaseResp.setLast(true);
        cursorPageBaseResp.setList(new ArrayList<T>());
        return cursorPageBaseResp;
    }

}