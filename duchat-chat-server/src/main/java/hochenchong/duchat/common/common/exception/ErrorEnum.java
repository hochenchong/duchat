package hochenchong.duchat.common.common.exception;

public interface ErrorEnum {
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    Integer getErrorCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getErrorMsg();
}
