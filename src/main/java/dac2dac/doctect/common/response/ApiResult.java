package dac2dac.doctect.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.constant.SuccessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값인 필드는 JSON에서 제외
public class ApiResult<T> {

    private int status;
    private String message;
    private T data;

    private ApiResult(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private ApiResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> ApiResult<T> success(SuccessCode code, T data) {
        return new ApiResult<>(code.getStatus().value(), code.getMessage(), data);
    }

    public static <T> ApiResult<T> success(SuccessCode code) {
        return new ApiResult<>(code.getStatus().value(), code.getMessage());
    }

    public static <T> ApiResult<T> error(ErrorCode code) {
        return new ApiResult<>(code.getStatus().value(), code.getMessage());
    }

    public static <T> ApiResult<T> error(HttpStatus status, String message) {
        return new ApiResult<>(status.value(), message);
    }

}

