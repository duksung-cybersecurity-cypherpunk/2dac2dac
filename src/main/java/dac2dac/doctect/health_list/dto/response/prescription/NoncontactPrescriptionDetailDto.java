package dac2dac.doctect.health_list.dto.response.prescription;

import dac2dac.doctect.doctor.entity.Medicine;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoncontactPrescriptionDetailDto {

    private List<Medicine> medicines;

    @Builder
    public NoncontactPrescriptionDetailDto(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
