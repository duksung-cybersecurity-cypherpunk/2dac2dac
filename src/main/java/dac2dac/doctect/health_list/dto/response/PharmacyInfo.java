package dac2dac.doctect.health_list.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PharmacyInfo {

    private LocalDateTime treatDate;

    private String agencyName;
    private String agencyAddress;
    private String agencyTel;

    @Builder
    public PharmacyInfo(LocalDateTime treatDate, String agencyName, String agencyAddress, String agencyTel) {
        this.treatDate = treatDate;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.agencyTel = agencyTel;
    }
}
