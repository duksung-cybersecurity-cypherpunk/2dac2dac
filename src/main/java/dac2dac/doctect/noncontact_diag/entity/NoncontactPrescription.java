package dac2dac.doctect.noncontact_diag.entity;

import dac2dac.doctect.bootpay.entity.PaymentInfo;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.entity.Medicine;
import dac2dac.doctect.health_list.entity.PrescriptionDrug;
import dac2dac.doctect.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoncontactPrescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noncontact_diag_id")
    private NoncontactDiag noncontactDiag;

    @OneToMany(mappedBy = "noncontactPrescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medicine> prescriptionDrugList = new ArrayList<>();

    public void addPrescriptionDrug(Medicine prescriptionDrug) {
        prescriptionDrugList.add(prescriptionDrug);
        prescriptionDrug.setNoncontactPrescription(this);
    }

    @Builder
    public NoncontactPrescription(User user, NoncontactDiag noncontactDiag, List<Medicine> prescriptionDrugList) {
        this.user = user;
        this.noncontactDiag = noncontactDiag;
        this.prescriptionDrugList = prescriptionDrugList;
    }

}
