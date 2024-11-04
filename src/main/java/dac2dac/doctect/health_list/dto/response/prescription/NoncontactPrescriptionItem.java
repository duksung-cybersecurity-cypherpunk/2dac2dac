package dac2dac.doctect.health_list.dto.response.prescription;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoncontactPrescriptionItem {

    private Long noncontactPrescriptionId;
    private LocalDateTime prescriptionDate;
    private String doctorName;

    @Builder
    public NoncontactPrescriptionItem(Long noncontactPrescriptionId, LocalDateTime prescriptionDate, String doctorName) {
        this.noncontactPrescriptionId = noncontactPrescriptionId;
        this.prescriptionDate = prescriptionDate;
        this.doctorName = doctorName;
    }
}
