package dac2dac.doctect.health_list.dto.response.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class NoncontactDiagItemList {

    private int totalCnt;
    private List<NoncontactDiagItem> noncontactDiagItemList = new ArrayList<>();

    @Builder
    public NoncontactDiagItemList(int totalCnt, List<NoncontactDiagItem> noncontactDiagItemList) {
        this.totalCnt = totalCnt;
        this.noncontactDiagItemList = noncontactDiagItemList;
    }
}
