package dac2dac.doctect.noncontact_diag.entity;

import dac2dac.doctect.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<NoncontactPrescriptionDrug> prescriptionDrugList = new ArrayList<>();

    public void addPrescriptionDrug(NoncontactPrescriptionDrug prescriptionDrug) {
        prescriptionDrugList.add(prescriptionDrug);
        prescriptionDrug.setNoncontactPrescription(this);
    }

    @Builder
    public NoncontactPrescription(User user, NoncontactDiag noncontactDiag, List<NoncontactPrescriptionDrug> prescriptionDrugList) {
        this.user = user;
        this.noncontactDiag = noncontactDiag;
        this.prescriptionDrugList = prescriptionDrugList;
    }

}
