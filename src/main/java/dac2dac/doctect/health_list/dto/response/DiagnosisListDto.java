package dac2dac.doctect.health_list.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiagnosisListDto {

    private ContactDiagItemListDto contactDiagItemListDto;
    private NoncontactDiagItemListDto noncontactDiagItemListDto;

    @Builder
    public DiagnosisListDto(ContactDiagItemListDto contactDiagItemListDto, NoncontactDiagItemListDto noncontactDiagItemListDto) {
        this.contactDiagItemListDto = contactDiagItemListDto;
        this.noncontactDiagItemListDto = noncontactDiagItemListDto;
    }
}
