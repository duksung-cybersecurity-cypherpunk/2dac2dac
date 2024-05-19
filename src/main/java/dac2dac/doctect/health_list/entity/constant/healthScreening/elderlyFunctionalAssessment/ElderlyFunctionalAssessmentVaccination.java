package dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyFunctionalAssessmentVaccination {
    INFLUENZA_REQUIRED("인플루엔자 접종 필요"),
    PNEUMOCOCCAL_REQUIRED("폐렴구균 접종 필요"),
    NO_VACCINATION_REQUIRED("접종 필요 없음");

    private final String ElderlyFunctionalAssessmentVaccinationType;

    public static ElderlyFunctionalAssessmentVaccination fromString(String text) {
        for (ElderlyFunctionalAssessmentVaccination b : ElderlyFunctionalAssessmentVaccination.values()) {
            if (b.ElderlyFunctionalAssessmentVaccinationType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
