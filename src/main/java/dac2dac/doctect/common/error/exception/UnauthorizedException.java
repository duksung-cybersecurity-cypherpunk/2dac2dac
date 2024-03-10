package dac2dac.doctect.common.error.exception;

import dac2dac.doctect.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
    private final ErrorCode code;

    public UnauthorizedException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
