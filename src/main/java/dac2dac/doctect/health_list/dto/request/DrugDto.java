package dac2dac.doctect.health_list.dto.request;

import dac2dac.doctect.health_list.entity.PrescriptionDrug;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DrugDto {

    private String drugName;
    private Integer prescriptionCnt;
    private Integer medicationDays;

    @Builder
    public DrugDto(String drugName, Integer prescriptionCnt, Integer medicationDays) {
        this.drugName = drugName;
        this.prescriptionCnt = prescriptionCnt;
        this.medicationDays = medicationDays;
    }

    public PrescriptionDrug toEntity() {
        return PrescriptionDrug.builder()
            .drugName(drugName)
            .prescriptionCnt(prescriptionCnt)
            .medicationDays(medicationDays)
            .build();
    }
}
