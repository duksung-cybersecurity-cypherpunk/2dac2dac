package dac2dac.doctect.health_list.dto.response.prescription;

import dac2dac.doctect.health_list.dto.response.PharmacyInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrescriptionDetailDto {

    PharmacyInfo agencyInfo;
    PrescriptionDrugItemList prescriptionDrugInfo;

    @Builder
    public PrescriptionDetailDto(PharmacyInfo agencyInfo, PrescriptionDrugItemList prescriptionDrugInfo) {
        this.agencyInfo = agencyInfo;
        this.prescriptionDrugInfo = prescriptionDrugInfo;
    }
}
