package dac2dac.doctect.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse<T> {
    private HttpStatus status;
    private String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse error(ErrorCode code) {
        return new ErrorResponse(code.getStatus(), code.getMessage());
    }
    public static ErrorResponse error(HttpStatus status, String message) {
        return new ErrorResponse(status, message);
    }
}