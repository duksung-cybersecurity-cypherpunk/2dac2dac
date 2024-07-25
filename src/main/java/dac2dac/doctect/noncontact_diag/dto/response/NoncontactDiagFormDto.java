package dac2dac.doctect.noncontact_diag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoncontactDiagFormDto {

    private NoncontactDiagFormDoctorInfo noncontactFormDoctorInfo;
    private NoncontactDiagFormInfo noncontactDiagFormInfo;

    @Builder
    public NoncontactDiagFormDto(NoncontactDiagFormDoctorInfo noncontactFormDoctorInfo, NoncontactDiagFormInfo noncontactDiagFormInfo) {
        this.noncontactFormDoctorInfo = noncontactFormDoctorInfo;
        this.noncontactDiagFormInfo = noncontactDiagFormInfo;
    }
}
