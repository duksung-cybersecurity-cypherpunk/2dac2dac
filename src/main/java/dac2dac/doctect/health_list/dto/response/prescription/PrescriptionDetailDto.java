package dac2dac.doctect.health_list.dto.response.prescription;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrescriptionDetailDto {

    PrescriptionItem prescriptionInfo;
    PrescriptionDrugItemList prescriptionDrugInfo;

    @Builder
    public PrescriptionDetailDto(PrescriptionItem prescriptionInfo, PrescriptionDrugItemList prescriptionDrugInfo) {
        this.prescriptionInfo = prescriptionInfo;
        this.prescriptionDrugInfo = prescriptionDrugInfo;
    }
}
