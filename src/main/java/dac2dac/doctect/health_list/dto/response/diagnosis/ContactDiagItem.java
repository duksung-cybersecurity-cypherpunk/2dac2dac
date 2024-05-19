package dac2dac.doctect.health_list.dto.response.diagnosis;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContactDiagItem {

    private Long diagId;

    private LocalDateTime diagDate;

    private String agencyName;
    private String agencyAddress;

    private String agencyThumbnail;

    private Integer agencyTodayOpenTime;
    private Integer agencyTodayCloseTime;
    private boolean agencyIsOpenNow;

    @Builder
    public ContactDiagItem(Long diagId, LocalDateTime diagDate, String agencyName, String agencyAddress, String agencyThumbnail, Integer agencyTodayOpenTime, Integer agencyTodayCloseTime,
        boolean agencyIsOpenNow) {
        this.diagId = diagId;
        this.diagDate = diagDate;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.agencyThumbnail = agencyThumbnail;
        this.agencyTodayOpenTime = agencyTodayOpenTime;
        this.agencyTodayCloseTime = agencyTodayCloseTime;
        this.agencyIsOpenNow = agencyIsOpenNow;
    }
}
