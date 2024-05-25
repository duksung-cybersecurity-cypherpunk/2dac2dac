package dac2dac.doctect.noncontact_diag.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorListDto {

    private Integer cnt;
    private List<DoctorItem> doctorItemList;

    @Builder
    public DoctorListDto(Integer cnt, List<DoctorItem> doctorItemList) {
        this.cnt = cnt;
        this.doctorItemList = doctorItemList;
    }
}
