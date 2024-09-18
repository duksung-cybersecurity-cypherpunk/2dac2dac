package dac2dac.doctect.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    GET_SUCCESS(HttpStatus.OK, "조회 성공"),
    SYNC_SUCCESS(HttpStatus.OK, "마이데이터 연동 성공"),
    PAYMENT_SUCCESS(HttpStatus.OK, "결제 성공"),
    CREATED_SUCCESS(HttpStatus.CREATED, "생성 성공"),
    ACCEPT_SUCCESS(HttpStatus.ACCEPTED, "수락 성공"),
    DELETE_SUCCESS(HttpStatus.OK, "삭제 성공");

    private final HttpStatus status;
    private final String message;

    public HttpStatus getStatusCode() {
        return status;
    }
}
