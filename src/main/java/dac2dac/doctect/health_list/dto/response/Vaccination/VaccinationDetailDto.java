package dac2dac.doctect.health_list.dto.response.Vaccination;

import dac2dac.doctect.health_list.dto.response.HostpitalInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VaccinationDetailDto {

    HostpitalInfo agencyInfo;
    VaccinationDetailInfo vaccinationDetailInfo;

    @Builder
    public VaccinationDetailDto(HostpitalInfo agencyInfo, VaccinationDetailInfo vaccinationDetailInfo) {
        this.agencyInfo = agencyInfo;
        this.vaccinationDetailInfo = vaccinationDetailInfo;
    }
}
