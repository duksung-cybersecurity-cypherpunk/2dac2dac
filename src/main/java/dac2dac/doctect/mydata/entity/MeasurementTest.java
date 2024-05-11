package dac2dac.doctect.mydata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
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

    private Double sight_left;
    private Double sight_right;

    private Integer hearing_left;
    private Integer hearing_right;

    private Integer blood_pressure_high;
    private Integer blood_pressure_low;

}
