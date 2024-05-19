package dac2dac.doctect.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    MYDATA_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "마이데이터 연동을 위한 본인 인증에 실패하셨습니다."),

    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "리소스 접근 권한이 없습니다."),

    /**
     * 404 Not Found
     */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "엔티티를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "병원을 찾을 수 없습니다."),
    PHARMACY_NOT_FOUND(HttpStatus.NOT_FOUND, "약국을 찾을 수 없습니다."),
    CONTACT_DIAGNOSIS_NOT_FOUND(HttpStatus.NOT_FOUND, "ID에 해당하는 대면 진료 내역을 찾을 수 없습니다."),
    PRESCRIPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "ID에 해당하는 투약 내역을 찾을 수 없습니다."),
    VACCINATION_NOT_FOUND(HttpStatus.NOT_FOUND, "ID에 해당하는 예방접종 내역을 찾을 수 없습니다."),
    NEARBY_PHARMACY_NOT_FOUND(HttpStatus.NOT_FOUND, "반경 2km 이내의 약국을 찾을 수 없습니다."),
    NEARBY_HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "반경 2km 이내의 병원을 찾을 수 없습니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),

    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final HttpStatus status;
    private final String message;

    public HttpStatus getStatusCode() {
        return status;
    }
}