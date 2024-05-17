package dac2dac.doctect.health_list.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class NoncontactDiagItemListDto {

    private int totalCnt;
    private List<NoncontactDiagItemDto> noncontactDiagItemList = new ArrayList<>();

    @Builder
    public NoncontactDiagItemListDto(int totalCnt, List<NoncontactDiagItemDto> noncontactDiagItemList) {
        this.totalCnt = totalCnt;
        this.noncontactDiagItemList = noncontactDiagItemList;
    }
}
