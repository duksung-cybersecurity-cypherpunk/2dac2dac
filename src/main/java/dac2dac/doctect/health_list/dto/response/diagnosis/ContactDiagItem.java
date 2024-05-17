package dac2dac.doctect.health_list.dto.response.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ContactDiagItem {

    private LocalDateTime diagDate;

    private String agencyName;
    private String agencyAddress;

    private String agencyThumbnail;

    private Integer agencyTodayOpenTime;
    private Integer agencyTodayCloseTime;
    private boolean agencyIsOpenNow;

    @Builder
    public ContactDiagItem(LocalDateTime diagDate, String agencyName, String agencyAddress, String agencyThumbnail, Integer agencyTodayOpenTime, Integer agencyTodayCloseTime, boolean agencyIsOpenNow) {
        this.diagDate = diagDate;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.agencyThumbnail = agencyThumbnail;
        this.agencyTodayOpenTime = agencyTodayOpenTime;
        this.agencyTodayCloseTime = agencyTodayCloseTime;
        this.agencyIsOpenNow = agencyIsOpenNow;
    }
}
