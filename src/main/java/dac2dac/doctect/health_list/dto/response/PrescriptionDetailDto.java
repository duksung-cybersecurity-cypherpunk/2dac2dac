package dac2dac.doctect.health_list.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PrescriptionDetailDto {

    PrescriptionItemDto prescriptionInfo;
    List<PrescriptionDrugDto> prescriptionDrugList = new ArrayList<>();

    @Builder
    public PrescriptionDetailDto(PrescriptionItemDto prescriptionInfo, List<PrescriptionDrugDto> prescriptionDrugList) {
        this.prescriptionInfo = prescriptionInfo;
        this.prescriptionDrugList = prescriptionDrugList;
    }
}
