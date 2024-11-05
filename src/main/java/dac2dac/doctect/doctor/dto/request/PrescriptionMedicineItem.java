package dac2dac.doctect.doctor.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrescriptionMedicineItem {

    private Long medicineId;
    private Integer prescriptionCnt;
    private Integer medicationDays;

}
