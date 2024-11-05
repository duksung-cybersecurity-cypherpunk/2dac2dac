package dac2dac.doctect.health_list.dto.response.prescription;

import dac2dac.doctect.doctor.dto.response.NoncontactPrescriptionDrugItem;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoncontactPrescriptionDetailDto {

    private List<NoncontactPrescriptionDrugItem> medicines;

    @Builder
    public NoncontactPrescriptionDetailDto(List<NoncontactPrescriptionDrugItem> medicines) {
        this.medicines = medicines;
    }
}
