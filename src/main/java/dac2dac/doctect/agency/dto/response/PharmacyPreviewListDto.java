package dac2dac.doctect.agency.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PharmacyPreviewListDto {

    private int totalCnt;
    private List<PharmacyPreviewDto> pharmacyPreviewList = new ArrayList<>();

    @Builder
    public PharmacyPreviewListDto(int totalCnt, List<PharmacyPreviewDto> pharmacyPreviewList) {
        this.totalCnt = totalCnt;
        this.pharmacyPreviewList = pharmacyPreviewList;
    }
}
