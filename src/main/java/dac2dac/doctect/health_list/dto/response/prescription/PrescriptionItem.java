package dac2dac.doctect.health_list.dto.response.prescription;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrescriptionItem {

    private Long prescriptionId;

    private LocalDateTime treatDate;

    private String agencyName;
    private String agencyAddress;
    private String agencyTel;

    @Builder
    public PrescriptionItem(Long prescriptionId, LocalDateTime treatDate, String agencyName, String agencyAddress, String agencyTel) {
        this.prescriptionId = prescriptionId;
        this.treatDate = treatDate;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.agencyTel = agencyTel;
    }
}
