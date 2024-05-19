package dac2dac.doctect.health_list.dto.response.Vaccination;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VaccinationDetailDto {

    VaccinationItem vaccinationInfo;
    VaccinationDetailInfo vaccinationDetailInfo;

    @Builder
    public VaccinationDetailDto(VaccinationItem vaccinationInfo, VaccinationDetailInfo vaccinationDetailInfo) {
        this.vaccinationInfo = vaccinationInfo;
        this.vaccinationDetailInfo = vaccinationDetailInfo;
    }
}
