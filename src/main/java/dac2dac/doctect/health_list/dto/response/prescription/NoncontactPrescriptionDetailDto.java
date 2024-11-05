package dac2dac.doctect.health_list.dto.response.prescription;

import dac2dac.doctect.noncontact_diag.entity.NoncontactPrescriptionDrug;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoncontactPrescriptionDetailDto {

    private List<NoncontactPrescriptionDrug> medicines;

    @Builder
    public NoncontactPrescriptionDetailDto(List<NoncontactPrescriptionDrug> medicines) {
        this.medicines = medicines;
    }
}
