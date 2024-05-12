package dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyFunctionalAssessmentUrinaryIncontinence {
    NORMAL("정상"),
    DYSURIA_SUSPECTED("배뇨장애 의심");

    private final String ElderlyFunctionalAssessmentUrinaryIncontinenceType;

    public static ElderlyFunctionalAssessmentUrinaryIncontinence fromString(String text) {
        for (ElderlyFunctionalAssessmentUrinaryIncontinence b : ElderlyFunctionalAssessmentUrinaryIncontinence.values()) {
            if (b.ElderlyFunctionalAssessmentUrinaryIncontinenceType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
