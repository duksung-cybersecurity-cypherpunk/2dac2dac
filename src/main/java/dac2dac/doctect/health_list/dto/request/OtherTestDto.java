package dac2dac.doctect.health_list.dto.request;

import dac2dac.doctect.health_list.entity.OtherTest;
import dac2dac.doctect.health_list.entity.constant.healthScreening.TBChestDisease;
import dac2dac.doctect.health_list.entity.constant.healthScreening.UrinaryProtein;
import dac2dac.doctect.health_list.entity.constant.healthScreening.boneDensityTest.BoneDensityTest;
import dac2dac.doctect.health_list.entity.constant.healthScreening.cognitiveDysfunction.CognitiveDysfunction;
import dac2dac.doctect.health_list.entity.constant.healthScreening.depression.Depression;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentADL;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentFalls;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentUrinaryIncontinence;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentVaccination;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyPhysicalFunctionTest.ElderlyPhysicalFunctionTest;
import dac2dac.doctect.health_list.entity.constant.healthScreening.hepB.HepB;
import dac2dac.doctect.health_list.entity.constant.healthScreening.hepB.HepBSurfaceAntibody;
import dac2dac.doctect.health_list.entity.constant.healthScreening.hepB.HepBSurfaceAntigen;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OtherTestDto {

    private UrinaryProtein urinaryProtein;
    private TBChestDisease TBChestDisease;

    // B형간염
    private Boolean isHepB;
    private HepBSurfaceAntibody hepBSurfaceAntibody;
    private HepBSurfaceAntigen hepBSurfaceAntigen;
    private HepB hepB;

    // 우울증
    private Boolean isDepression;
    private Depression depression;

    // 인지기능장애
    private Boolean isCognitiveDysfunction;
    private CognitiveDysfunction cognitiveDysfunction;

    // 골밀도 검사
    private Boolean isBoneDensityTest;
    private BoneDensityTest boneDensityTest;

    // 노인신체기능 검사
    private Boolean isElderlyPhysicalFunctionTest;
    private ElderlyPhysicalFunctionTest elderlyPhysicalFunctionTest;

    // 노인기능평가
    private Boolean isElderlyFunctionalAssessment;
    private ElderlyFunctionalAssessmentFalls elderlyFunctionalAssessmentFalls;
    private ElderlyFunctionalAssessmentADL elderlyFunctionalAssessmentADL;
    private ElderlyFunctionalAssessmentVaccination elderlyFunctionalAssessmentVaccination;
    private ElderlyFunctionalAssessmentUrinaryIncontinence elderlyFunctionalAssessmentUrinaryIncontinence;

    private Boolean smoke;
    private Boolean drink;
    private Boolean physicalActivity;
    private Boolean exercise;

    @Builder
    public OtherTestDto(UrinaryProtein urinaryProtein, dac2dac.doctect.health_list.entity.constant.healthScreening.TBChestDisease TBChestDisease, Boolean isHepB,
        HepBSurfaceAntibody hepBSurfaceAntibody,
        HepBSurfaceAntigen hepBSurfaceAntigen, HepB hepB, Boolean isDepression, Depression depression, Boolean isCognitiveDysfunction, CognitiveDysfunction cognitiveDysfunction,
        Boolean isBoneDensityTest,
        BoneDensityTest boneDensityTest, Boolean isElderlyPhysicalFunctionTest, ElderlyPhysicalFunctionTest elderlyPhysicalFunctionTest, Boolean isElderlyFunctionalAssessment,
        ElderlyFunctionalAssessmentFalls elderlyFunctionalAssessmentFalls, ElderlyFunctionalAssessmentADL elderlyFunctionalAssessmentADL,
        ElderlyFunctionalAssessmentVaccination elderlyFunctionalAssessmentVaccination, ElderlyFunctionalAssessmentUrinaryIncontinence elderlyFunctionalAssessmentUrinaryIncontinence, Boolean smoke,
        Boolean drink, Boolean physicalActivity, Boolean exercise) {
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

    public OtherTest toEntity() {
        return OtherTest.builder()
            .urinaryProtein(urinaryProtein)
            .TBChestDisease(TBChestDisease)
            .isHepB(isHepB)
            .hepBSurfaceAntibody(hepBSurfaceAntibody)
            .hepBSurfaceAntigen(hepBSurfaceAntigen)
            .hepB(hepB)
            .isDepression(isDepression)
            .depression(depression)
            .isCognitiveDysfunction(isCognitiveDysfunction)
            .cognitiveDysfunction(cognitiveDysfunction)
            .isBoneDensityTest(isBoneDensityTest)
            .boneDensityTest(boneDensityTest)
            .isElderlyPhysicalFunctionTest(isElderlyPhysicalFunctionTest)
            .elderlyPhysicalFunctionTest(elderlyPhysicalFunctionTest)
            .isElderlyFunctionalAssessment(isElderlyFunctionalAssessment)
            .elderlyFunctionalAssessmentFalls(elderlyFunctionalAssessmentFalls)
            .elderlyFunctionalAssessmentADL(elderlyFunctionalAssessmentADL)
            .elderlyFunctionalAssessmentVaccination(elderlyFunctionalAssessmentVaccination)
            .elderlyFunctionalAssessmentUrinaryIncontinence(elderlyFunctionalAssessmentUrinaryIncontinence)
            .smoke(smoke)
            .drink(drink)
            .physicalActivity(physicalActivity)
            .exercise(exercise)
            .build();
    }
}