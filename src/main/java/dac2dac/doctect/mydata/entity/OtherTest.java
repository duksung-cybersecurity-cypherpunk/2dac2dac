package dac2dac.doctect.mydata.entity;

import dac2dac.doctect.mydata.entity.constant.healthScreening.TBChestDisease;
import dac2dac.doctect.mydata.entity.constant.healthScreening.UrinaryProtein;
import dac2dac.doctect.mydata.entity.constant.healthScreening.boneDensityTest.BoneDensityTest;
import dac2dac.doctect.mydata.entity.constant.healthScreening.cognitiveDysfunction.CognitiveDysfunction;
import dac2dac.doctect.mydata.entity.constant.healthScreening.depression.Depression;
import dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentADL;
import dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentFalls;
import dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentUrinaryIncontinence;
import dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentVaccination;
import dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyPhysicalFunctionTest.ElderlyPhysicalFunctionTest;
import dac2dac.doctect.mydata.entity.constant.healthScreening.hepB.HepB;
import dac2dac.doctect.mydata.entity.constant.healthScreening.hepB.HepBSurfaceAntibody;
import dac2dac.doctect.mydata.entity.constant.healthScreening.hepB.HepBSurfaceAntigen;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OtherTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UrinaryProtein urinaryProtein;

    @Enumerated(EnumType.STRING)
    private TBChestDisease TBChestDisease;

    // B형간염
    private Boolean isHepB;

    @Enumerated(EnumType.STRING)
    private HepBSurfaceAntibody hepBSurfaceAntibody;

    @Enumerated(EnumType.STRING)
    private HepBSurfaceAntigen hepBSurfaceAntigen;

    @Enumerated(EnumType.STRING)
    private HepB hepB;

    // 우울증
    private Boolean isDepression;

    @Enumerated(EnumType.STRING)
    private Depression depression;

    // 인지기능장애
    private Boolean isCognitiveDysfunction;

    @Enumerated(EnumType.STRING)
    private CognitiveDysfunction cognitiveDysfunction;

    // 골밀도 검사
    private Boolean isBoneDensityTest;

    @Enumerated(EnumType.STRING)
    private BoneDensityTest boneDensityTest;

    // 노인신체기능 검사
    private Boolean isElderlyPhysicalFunctionTest;

    @Enumerated(EnumType.STRING)
    private ElderlyPhysicalFunctionTest elderlyPhysicalFunctionTest;

    // 노인기능평가
    private Boolean isElderlyFunctionalAssessment;

    @Enumerated(EnumType.STRING)
    private ElderlyFunctionalAssessmentFalls elderlyFunctionalAssessmentFalls;

    @Enumerated(EnumType.STRING)
    private ElderlyFunctionalAssessmentADL elderlyFunctionalAssessmentADL;

    @Enumerated(EnumType.STRING)
    private ElderlyFunctionalAssessmentVaccination elderlyFunctionalAssessmentVaccination;

    @Enumerated(EnumType.STRING)
    private ElderlyFunctionalAssessmentUrinaryIncontinence elderlyFunctionalAssessmentUrinaryIncontinence;

    private Boolean smoke;
    private Boolean drink;
    private Boolean physicalActivity;
    private Boolean exercise;

}
