package dac2dac.doctect.noncontact_diag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorDto {

    private DoctorInfo doctorInfo;

    private DoctorIntroduction doctorIntroduction;
    @Builder
    public DoctorDto(DoctorInfo doctorInfo, DoctorIntroduction doctorIntroduction) {
        this.doctorInfo = doctorInfo;
        this.doctorIntroduction = doctorIntroduction;
    }
}
