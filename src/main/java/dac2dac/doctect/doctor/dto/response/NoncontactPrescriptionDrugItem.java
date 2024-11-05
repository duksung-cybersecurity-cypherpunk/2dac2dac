package dac2dac.doctect.doctor.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoncontactPrescriptionDrugItem {

    private String medicineName;
    private String medicineClassName;
    private String medicineImageUrl;
    private String medicineChart;

    private Integer prescriptionCnt;
    private Integer medicationDays;

    @Builder
    public NoncontactPrescriptionDrugItem(String medicineName, String medicineClassName, String medicineImageUrl, String medicineChart, Integer prescriptionCnt, Integer medicationDays) {
        this.medicineName = medicineName;
        this.medicineClassName = medicineClassName;
        this.medicineImageUrl = medicineImageUrl;
        this.medicineChart = medicineChart;
        this.prescriptionCnt = prescriptionCnt;
        this.medicationDays = medicationDays;
    }
}
