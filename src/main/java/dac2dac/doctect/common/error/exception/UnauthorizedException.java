package dac2dac.doctect.common.error.exception;

import dac2dac.doctect.common.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends RuntimeException{
//    private final ErrorCode code;
    private final HttpStatus statusCode;
    private final String message;

    public UnauthorizedException(ErrorCode code) {
        super(code.getMessage());
//        this.code = code;
        this.statusCode = code.getStatusCode();
        this.message = code.getMessage();
    }

    public UnauthorizedException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }
}
