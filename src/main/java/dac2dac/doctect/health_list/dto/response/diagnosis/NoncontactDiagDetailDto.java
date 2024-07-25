package dac2dac.doctect.health_list.dto.response.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoncontactDiagDetailDto {

    private NoncontactDoctorInfo noncontactDoctorInfo;
    private NoncontactDiagDetailInfo noncontactDiagDetailInfo;

    @Builder
    public NoncontactDiagDetailDto(NoncontactDoctorInfo noncontactDoctorInfo, NoncontactDiagDetailInfo noncontactDiagDetailInfo) {
        this.noncontactDoctorInfo = noncontactDoctorInfo;
        this.noncontactDiagDetailInfo = noncontactDiagDetailInfo;
    }
}
