package dac2dac.doctect.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    /**
     * 예시
     */
    EXAMPLE_SUCCESS(HttpStatus.OK, "예시 성공"),
    GET_SUCCESS(HttpStatus.OK, "조회 성공");


    private final HttpStatus status;
    private final String message;

    public HttpStatus getStatusCode() {
        return status;
    }
}
