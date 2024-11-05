package dac2dac.doctect.noncontact_diag.entity;

import dac2dac.doctect.doctor.entity.Medicine;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoncontactPrescriptionDrug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private Integer prescriptionCnt;
    private Integer medicationDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noncontact_prescription_id")
    private NoncontactPrescription noncontactPrescription;

    @Builder
    public NoncontactPrescriptionDrug(Medicine medicine, Integer prescriptionCnt, Integer medicationDays, NoncontactPrescription noncontactPrescription) {
        this.medicine = medicine;
        this.prescriptionCnt = prescriptionCnt;
        this.medicationDays = medicationDays;
        this.noncontactPrescription = noncontactPrescription;
    }

    public void setNoncontactPrescription(NoncontactPrescription noncontactPrescription) {
        this.noncontactPrescription = noncontactPrescription;
    }
}
