package dac2dac.doctect.health_list.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ContactDiagItemListDto {

    private int totalCnt;
    private List<ContactDiagItemDto> contactDiagItemList = new ArrayList<>();

    @Builder
    public ContactDiagItemListDto(int totalCnt, List<ContactDiagItemDto> contactDiagItemList) {
        this.totalCnt = totalCnt;
        this.contactDiagItemList = contactDiagItemList;
    }
}
