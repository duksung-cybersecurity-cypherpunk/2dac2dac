package dac2dac.doctect.common.error.exception;

import dac2dac.doctect.common.constant.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final ErrorCode code;

    public ForbiddenException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}