package dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyFunctionalAssessment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyFunctionalAssessmentADL {
    NORMAL("정상"),
    ASSISTANCE_REQUIRED("일상생활 도움 필요");

    private final String DiagTypeName;
}
