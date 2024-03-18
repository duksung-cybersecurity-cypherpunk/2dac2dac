package dac2dac.doctect.noncontact_diag.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiagStatus {
    TREATMENTING("진료중"),
    COMPLETE("진료완료");

    private final String DiagStatus;
}
