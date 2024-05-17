package dac2dac.doctect.health_list.dto.response.prescription;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PrescriptionDrugItemList {

    List<PrescriptionDrugItem> prescriptionDrugList = new ArrayList<>();

    @Builder
    public PrescriptionDrugItemList(List<PrescriptionDrugItem> prescriptionDrugList) {
        this.prescriptionDrugList = prescriptionDrugList;
    }
}
