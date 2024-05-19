package dac2dac.doctect.health_list.dto.response.healthScreening;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthScreeningItemListDto {

    private int totalCnt;
    private List<HealthScreeningItem> healthScreeningItemList = new ArrayList<>();

    @Builder
    public HealthScreeningItemListDto(int totalCnt, List<HealthScreeningItem> healthScreeningItemList) {
        this.totalCnt = totalCnt;
        this.healthScreeningItemList = healthScreeningItemList;
    }
}
