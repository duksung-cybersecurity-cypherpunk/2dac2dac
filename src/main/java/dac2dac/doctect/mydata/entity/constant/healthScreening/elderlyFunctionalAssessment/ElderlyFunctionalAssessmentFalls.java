package dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyFunctionalAssessment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyFunctionalAssessmentFalls {
    NORMAL("정상"),
    HIGH_RISK_FOR_FALL("낙상 고위험자");

    private final String DiagTypeName;
}
