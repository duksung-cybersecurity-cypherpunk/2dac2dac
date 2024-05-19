package dac2dac.doctect.health_list.dto.response.healthScreening;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BloodTestInfo {

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

    @Builder
    public BloodTestInfo(Double hemoglobin, Double fastingBloodSugar, Double totalCholesterol, Double HDLCholesterol, Double triglyceride, Double LDLCholesterol, Double serumCreatinine, Integer GFR,
        Integer AST, Integer ALT, Integer GPT) {
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
