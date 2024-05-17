package dac2dac.doctect.health_list.dto.response.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiagDetailInfo {

    private String diagType;

    private Integer prescription_cnt;
    private Integer medication_cnt;
    private Integer visit_days;

    @Builder
    public DiagDetailInfo(String diagType, Integer prescription_cnt, Integer medication_cnt, Integer visit_days) {
        this.diagType = diagType;
        this.prescription_cnt = prescription_cnt;
        this.medication_cnt = medication_cnt;
        this.visit_days = visit_days;
    }
}
