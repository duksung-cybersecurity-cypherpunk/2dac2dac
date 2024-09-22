package dac2dac.doctect.noncontact_diag.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RejectionReason {
    DUPLICATE_RESERVATION,     // 같은 시간대 중복 예약
    SPAM_RESERVATION,          // 예약 도배
    OUT_OF_SCOPE_SYMPTOM,      // 본원 진료 분야에 해당하지 않는 증상
    DOCTOR_PERSONAL_REASON,    // 의사 개인 사정
    OTHER                      // 기타
}