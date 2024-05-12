package dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyFunctionalAssessmentFalls {
    NORMAL("정상"),
    HIGH_RISK_FOR_FALL("낙상 고위험자");

    private final String ElderlyFunctionalAssessmentFallsType;

    public static ElderlyFunctionalAssessmentFalls fromString(String text) {
        for (ElderlyFunctionalAssessmentFalls b : ElderlyFunctionalAssessmentFalls.values()) {
            if (b.ElderlyFunctionalAssessmentFallsType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
