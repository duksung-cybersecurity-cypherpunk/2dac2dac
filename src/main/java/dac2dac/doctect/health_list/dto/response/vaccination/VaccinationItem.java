package dac2dac.doctect.health_list.dto.response.vaccination;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VaccinationItem {

    private Long vaccId;

    private LocalDateTime vaccDate;
    private String vaccName;
    private Integer vaccSeries;

    private String agencyName;
    private String agencyAddress;

    private String agencyThumbnail;

    private Integer agencyTodayOpenTime;
    private Integer agencyTodayCloseTime;
    private boolean agencyIsOpenNow;

    @Builder
    public VaccinationItem(Long vaccId, LocalDateTime vaccDate, String vaccName, Integer vaccSeries, String agencyName, String agencyAddress, String agencyThumbnail, Integer agencyTodayOpenTime,
        Integer agencyTodayCloseTime, boolean agencyIsOpenNow) {
        this.vaccId = vaccId;
        this.vaccDate = vaccDate;
        this.vaccName = vaccName;
        this.vaccSeries = vaccSeries;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.agencyThumbnail = agencyThumbnail;
        this.agencyTodayOpenTime = agencyTodayOpenTime;
        this.agencyTodayCloseTime = agencyTodayCloseTime;
        this.agencyIsOpenNow = agencyIsOpenNow;
    }
}
