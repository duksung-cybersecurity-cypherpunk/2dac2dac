package dac2dac.doctect.health_list.dto.response.prescription;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PrescriptionItemListDto {

    private int totalCnt;
    private List<PrescriptionItem> prescriptionItemList = new ArrayList<>();
    private List<NoncontactPrescriptionItem> noncontactPrescriptionItemList = new ArrayList<>();

    @Builder
    public PrescriptionItemListDto(int totalCnt, List<PrescriptionItem> prescriptionItemList, List<NoncontactPrescriptionItem> noncontactPrescriptionItemList) {
        this.totalCnt = totalCnt;
        this.prescriptionItemList = prescriptionItemList;
        this.noncontactPrescriptionItemList = noncontactPrescriptionItemList;
    }
}
