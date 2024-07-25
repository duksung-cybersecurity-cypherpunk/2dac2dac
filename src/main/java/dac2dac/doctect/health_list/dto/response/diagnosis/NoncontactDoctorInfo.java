package dac2dac.doctect.health_list.dto.response.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoncontactDoctorInfo {

    private Long doctorId;
    private LocalDateTime diagDate;

    private String doctorName;
    private String doctorHospitalName;

    private String doctorThumbnail;

    private Integer doctorTodayOpenTime;
    private Integer doctorTodayCloseTime;
    private boolean doctorIsOpenNow;

    @Builder
    public NoncontactDoctorInfo(Long doctorId, LocalDateTime diagDate, String doctorName, String doctorHospitalName, String doctorThumbnail, Integer doctorTodayOpenTime, Integer doctorTodayCloseTime, boolean doctorIsOpenNow) {
        this.doctorId = doctorId;
        this.diagDate = diagDate;
        this.doctorName = doctorName;
        this.doctorHospitalName = doctorHospitalName;
        this.doctorThumbnail = doctorThumbnail;
        this.doctorTodayOpenTime = doctorTodayOpenTime;
        this.doctorTodayCloseTime = doctorTodayCloseTime;
        this.doctorIsOpenNow = doctorIsOpenNow;
    }
}
