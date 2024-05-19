package dac2dac.doctect.health_list.dto.response.healthScreening;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MeasurementTestInfo {

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

    @Builder
    public MeasurementTestInfo(Double height, Double weight, Double waist, Double bmi, Double sightLeft, Double sightRight, Integer hearingLeft, Integer hearingRight, Integer bloodPressureHigh,
        Integer bloodPressureLow) {
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
