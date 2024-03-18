package dac2dac.doctect.common.error.exception;

import dac2dac.doctect.common.constant.ErrorCode;
import lombok.Getter;

@Getter
public class NoSuchElementException extends RuntimeException{
    private final ErrorCode code;

    public NoSuchElementException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}