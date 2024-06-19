package dac2dac.doctect.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    GET_SUCCESS(HttpStatus.OK, "조회 성공"),
    SYNC_SUCCESS(HttpStatus.OK, "마이데이터 연동 성공"),
    CREATED_SUCCESS(HttpStatus.CREATED, "생성 성공");

    private final HttpStatus status;
    private final String message;

    public HttpStatus getStatusCode() {
        return status;
    }
}
