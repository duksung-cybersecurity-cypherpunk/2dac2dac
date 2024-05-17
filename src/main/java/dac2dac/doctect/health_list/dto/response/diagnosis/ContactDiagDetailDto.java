package dac2dac.doctect.health_list.dto.response.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContactDiagDetailDto {

    private ContactDiagItem contactDiagInfo;
    private DiagDetailInfo diagDetailInfo;

    @Builder
    public ContactDiagDetailDto(ContactDiagItem contactDiagInfo, DiagDetailInfo diagDetailInfo) {
        this.contactDiagInfo = contactDiagInfo;
        this.diagDetailInfo = diagDetailInfo;
    }
}
