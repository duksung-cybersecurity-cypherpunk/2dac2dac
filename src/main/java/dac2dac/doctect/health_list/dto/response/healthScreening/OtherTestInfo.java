package dac2dac.doctect.health_list.dto.response.healthScreening;

import dac2dac.doctect.health_list.entity.constant.healthScreening.hepB.HepB;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OtherTestInfo {

    private String urinaryProtein;
    private String TBChestDisease;

    // B형간염
    private Boolean isHepB;
    private String hepBSurfaceAntibody;
    private String hepBSurfaceAntigen;
    private HepB hepB;

    // 우울증
    private Boolean isDepression;
    private String depression;

    // 인지기능장애
    private Boolean isCognitiveDysfunction;
    private String cognitiveDysfunction;

    // 골밀도 검사
    private Boolean isBoneDensityTest;
    private String boneDensityTest;

    // 노인신체기능 검사
    private Boolean isElderlyPhysicalFunctionTest;
    private String elderlyPhysicalFunctionTest;

    // 노인기능평가
    private Boolean isElderlyFunctionalAssessment;
    private String elderlyFunctionalAssessmentFalls;
    private String elderlyFunctionalAssessmentADL;
    private String elderlyFunctionalAssessmentVaccination;
    private String elderlyFunctionalAssessmentUrinaryIncontinence;

    private Boolean smoke;
    private Boolean drink;
    private Boolean physicalActivity;
    private Boolean exercise;

    @Builder
    public OtherTestInfo(String urinaryProtein, String TBChestDisease, Boolean isHepB, String hepBSurfaceAntibody, String hepBSurfaceAntigen, HepB hepB, Boolean isDepression, String depression,
        Boolean isCognitiveDysfunction, String cognitiveDysfunction, Boolean isBoneDensityTest, String boneDensityTest, Boolean isElderlyPhysicalFunctionTest, String elderlyPhysicalFunctionTest,
        Boolean isElderlyFunctionalAssessment, String elderlyFunctionalAssessmentFalls, String elderlyFunctionalAssessmentADL, String elderlyFunctionalAssessmentVaccination,
        String elderlyFunctionalAssessmentUrinaryIncontinence, Boolean smoke, Boolean drink, Boolean physicalActivity, Boolean exercise) {
        this.urinaryProtein = urinaryProtein;
        this.TBChestDisease = TBChestDisease;
        this.isHepB = isHepB;
        this.hepBSurfaceAntibody = hepBSurfaceAntibody;
        this.hepBSurfaceAntigen = hepBSurfaceAntigen;
        this.hepB = hepB;
        this.isDepression = isDepression;
        this.depression = depression;
        this.isCognitiveDysfunction = isCognitiveDysfunction;
        this.cognitiveDysfunction = cognitiveDysfunction;
        this.isBoneDensityTest = isBoneDensityTest;
        this.boneDensityTest = boneDensityTest;
        this.isElderlyPhysicalFunctionTest = isElderlyPhysicalFunctionTest;
        this.elderlyPhysicalFunctionTest = elderlyPhysicalFunctionTest;
        this.isElderlyFunctionalAssessment = isElderlyFunctionalAssessment;
        this.elderlyFunctionalAssessmentFalls = elderlyFunctionalAssessmentFalls;
        this.elderlyFunctionalAssessmentADL = elderlyFunctionalAssessmentADL;
        this.elderlyFunctionalAssessmentVaccination = elderlyFunctionalAssessmentVaccination;
        this.elderlyFunctionalAssessmentUrinaryIncontinence = elderlyFunctionalAssessmentUrinaryIncontinence;
        this.smoke = smoke;
        this.drink = drink;
        this.physicalActivity = physicalActivity;
        this.exercise = exercise;
    }
}
