package dac2dac.doctect.noncontact_diag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorDto {

    private DoctorInfo doctorInfo;

    private DoctorIntroduction doctorIntroduction;

    private DoctorReviewList doctorReviewList;

    @Builder
    public DoctorDto(DoctorInfo doctorInfo, DoctorIntroduction doctorIntroduction, DoctorReviewList doctorReviewList) {
        this.doctorInfo = doctorInfo;
        this.doctorIntroduction = doctorIntroduction;
        this.doctorReviewList = doctorReviewList;
    }
}
