package dac2dac.doctect.health_list.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BloodTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double hemoglobin;
    private Double fastingBloodSugar;
    private Double totalCholesterol;
    private Double HDLCholesterol;
    private Double triglyceride;
    private Double LDLCholesterol;
    private Double serumCreatinine;

    private Integer GFR;
    private Integer AST;
    private Integer ALT;
    private Integer GPT;

    @OneToOne
    @JoinColumn(name = "health_screening_id")
    private HealthScreening healthScreening;

    // HealthScreening 참조를 설정하는 setter 메서드
    public void setHealthScreening(HealthScreening healthScreening) {
        this.healthScreening = healthScreening;
    }

    @Builder
    public BloodTest(HealthScreening healthScreening, Double hemoglobin, Double fastingBloodSugar, Double totalCholesterol, Double HDLCholesterol, Double triglyceride, Double LDLCholesterol,
        Double serumCreatinine, Integer GFR, Integer AST, Integer ALT, Integer GPT) {
        this.healthScreening = healthScreening;
        this.hemoglobin = hemoglobin;
        this.fastingBloodSugar = fastingBloodSugar;
        this.totalCholesterol = totalCholesterol;
        this.HDLCholesterol = HDLCholesterol;
        this.triglyceride = triglyceride;
        this.LDLCholesterol = LDLCholesterol;
        this.serumCreatinine = serumCreatinine;
        this.GFR = GFR;
        this.AST = AST;
        this.ALT = ALT;
        this.GPT = GPT;
    }
}
