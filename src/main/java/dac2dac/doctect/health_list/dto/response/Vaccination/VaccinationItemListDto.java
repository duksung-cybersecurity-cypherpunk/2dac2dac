package dac2dac.doctect.health_list.dto.response.Vaccination;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VaccinationItemListDto {

    private int totalCnt;
    private List<VaccinationItem> vaccinationItemList = new ArrayList<>();

    @Builder
    public VaccinationItemListDto(int totalCnt, List<VaccinationItem> vaccinationItemList) {
        this.totalCnt = totalCnt;
        this.vaccinationItemList = vaccinationItemList;
    }
}
