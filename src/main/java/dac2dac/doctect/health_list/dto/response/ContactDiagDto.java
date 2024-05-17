package dac2dac.doctect.health_list.dto.response;

import dac2dac.doctect.health_list.entity.constant.DiagType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ContactDiagDto {

    private String agencyName;
    private String agencyAddress;

    private String agencyThumbnail;

    private Integer agencyTodayOpenTime;
    private Integer agencyTodayCloseTime;
    private boolean agencyIsOpenNow;

    private LocalDateTime diagDate;
    private DiagType diagType;

    private Integer prescription_cnt;
    private Integer medication_cnt;
    private Integer visit_days;

    @Builder
    public ContactDiagDto(String agencyName, String agencyAddress, String agencyThumbnail, Integer agencyTodayOpenTime, Integer agencyTodayCloseTime, boolean agencyIsOpenNow, LocalDateTime diagDate, DiagType diagType, Integer prescription_cnt, Integer medication_cnt, Integer visit_days) {
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.agencyThumbnail = agencyThumbnail;
        this.agencyTodayOpenTime = agencyTodayOpenTime;
        this.agencyTodayCloseTime = agencyTodayCloseTime;
        this.agencyIsOpenNow = agencyIsOpenNow;
        this.diagDate = diagDate;
        this.diagType = diagType;
        this.prescription_cnt = prescription_cnt;
        this.medication_cnt = medication_cnt;
        this.visit_days = visit_days;
    }
}
