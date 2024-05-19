package dac2dac.doctect.health_list.dto.response.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiagnosisListDto {

    private ContactDiagItemList contactDiagList;
    private NoncontactDiagItemList noncontactDiagList;

    @Builder
    public DiagnosisListDto(ContactDiagItemList contactDiagList, NoncontactDiagItemList noncontactDiagList) {
        this.contactDiagList = contactDiagList;
        this.noncontactDiagList = noncontactDiagList;
    }
}
