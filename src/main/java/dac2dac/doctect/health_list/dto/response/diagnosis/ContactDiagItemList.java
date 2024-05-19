package dac2dac.doctect.health_list.dto.response.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ContactDiagItemList {

    private int totalCnt;
    private List<ContactDiagItem> contactDiagItemList = new ArrayList<>();

    @Builder
    public ContactDiagItemList(int totalCnt, List<ContactDiagItem> contactDiagItemList) {
        this.totalCnt = totalCnt;
        this.contactDiagItemList = contactDiagItemList;
    }
}
