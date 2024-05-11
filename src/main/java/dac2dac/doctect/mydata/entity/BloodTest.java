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

}
