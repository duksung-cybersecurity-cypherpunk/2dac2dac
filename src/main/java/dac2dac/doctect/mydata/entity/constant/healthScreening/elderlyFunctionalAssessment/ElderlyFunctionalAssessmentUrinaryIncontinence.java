package dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyFunctionalAssessment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyFunctionalAssessmentUrinaryIncontinence {
    NORMAL("정상"),
    DYSURIA_SUSPECTED("배뇨장애 의심");

    private final String DiagTypeName;
}
