package hochenchong.duchat.common.user.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hochenchong
 * @date 2024/07/29
 */
@Data
public class IpResult<T> implements Serializable {
    private int code;

    private String msg;

    private T data;

    public boolean isSuccess() {
        return this.code == 0;
    }
}
