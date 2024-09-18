package dac2dac.doctect.doctor.dto.request;

import dac2dac.doctect.noncontact_diag.entity.constant.RejectionReason;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RejectReservationRequest {

    @NotNull(message = "거절 사유는 필수 입력 항목입니다.")
    private RejectionReason rejectionReason;

    @Size(min = 0, max = 30, message = "기타 사유는 30자 이하로 입력해 주세요.")
    private String additionalReason; // 기타 사유가 있을 경우 추가 사유를 입력 받음
}
