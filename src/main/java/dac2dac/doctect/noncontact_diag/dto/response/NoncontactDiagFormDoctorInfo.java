package dac2dac.doctect.noncontact_diag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class NoncontactDiagFormDoctorInfo {

    private Long doctorId;

    private String doctorName;
    private String doctorHospitalName;

    private String doctorThumbnail;

    private Integer doctorTodayOpenTime;
    private Integer doctorTodayCloseTime;
    private boolean doctorIsOpenNow;

    @Builder
    public NoncontactDiagFormDoctorInfo(Long doctorId, String doctorName, String doctorHospitalName, String doctorThumbnail, Integer doctorTodayOpenTime, Integer doctorTodayCloseTime, boolean doctorIsOpenNow) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorHospitalName = doctorHospitalName;
        this.doctorThumbnail = doctorThumbnail;
        this.doctorTodayOpenTime = doctorTodayOpenTime;
        this.doctorTodayCloseTime = doctorTodayCloseTime;
        this.doctorIsOpenNow = doctorIsOpenNow;
    }
}
