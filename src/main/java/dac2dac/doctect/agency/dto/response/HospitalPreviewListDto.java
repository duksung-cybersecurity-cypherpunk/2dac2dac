package dac2dac.doctect.agency.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HospitalPreviewListDto {

    private int totalCnt;
    private List<HospitalPreviewDto> HospitalPreviewList = new ArrayList<>();

    @Builder
    public HospitalPreviewListDto(int totalCnt, List<HospitalPreviewDto> hospitalPreviewList) {
        this.totalCnt = totalCnt;
        this.HospitalPreviewList = hospitalPreviewList;
    }
}
