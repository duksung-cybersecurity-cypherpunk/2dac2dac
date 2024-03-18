package dac2dac.doctect.common.error.exception;

import dac2dac.doctect.common.constant.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final ErrorCode code;

    public NotFoundException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
