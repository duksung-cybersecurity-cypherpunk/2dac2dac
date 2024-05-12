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
public class MeasurementTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double height;
    private Double weight;
    private Double waist;
    private Double bmi;

    private Double sightLeft;
    private Double sightRight;

    private Integer hearingLeft;
    private Integer hearingRight;

    private Integer bloodPressureHigh;
    private Integer bloodPressureLow;

    @OneToOne
    @JoinColumn(name = "health_screening_id")
    private HealthScreening healthScreening;

    // HealthScreening 참조를 설정하는 setter 메서드
    public void setHealthScreening(HealthScreening healthScreening) {
        this.healthScreening = healthScreening;
    }

    @Builder
    public MeasurementTest(HealthScreening healthScreening, Double height, Double weight, Double waist, Double bmi, Double sightLeft, Double sightRight, Integer hearingLeft, Integer hearingRight,
        Integer bloodPressureHigh, Integer bloodPressureLow) {
        this.healthScreening = healthScreening;
        this.height = height;
        this.weight = weight;
        this.waist = waist;
        this.bmi = bmi;
        this.sightLeft = sightLeft;
        this.sightRight = sightRight;
        this.hearingLeft = hearingLeft;
        this.hearingRight = hearingRight;
        this.bloodPressureHigh = bloodPressureHigh;
        this.bloodPressureLow = bloodPressureLow;
    }
}
