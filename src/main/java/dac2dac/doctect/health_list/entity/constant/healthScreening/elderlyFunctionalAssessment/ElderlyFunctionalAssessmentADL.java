package dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyFunctionalAssessmentADL {
    NORMAL("정상"),
    ASSISTANCE_REQUIRED("일상생활 도움 필요");

    private final String ElderlyFunctionalAssessmentADLType;

    public static ElderlyFunctionalAssessmentADL fromString(String text) {
        for (ElderlyFunctionalAssessmentADL b : ElderlyFunctionalAssessmentADL.values()) {
            if (b.ElderlyFunctionalAssessmentADLType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
