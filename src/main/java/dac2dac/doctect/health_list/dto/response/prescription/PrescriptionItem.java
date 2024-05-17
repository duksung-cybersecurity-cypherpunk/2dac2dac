package dac2dac.doctect.health_list.dto.response.prescription;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PrescriptionItem {

    private LocalDateTime treatDate;

    private String agencyName;
    private String agencyAddress;
    private String agencyTel;

    @Builder
    public PrescriptionItem(LocalDateTime treatDate, String agencyName, String agencyAddress, String agencyTel) {
        this.treatDate = treatDate;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.agencyTel = agencyTel;
    }
}
