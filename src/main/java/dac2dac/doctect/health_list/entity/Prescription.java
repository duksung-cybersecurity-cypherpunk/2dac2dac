package dac2dac.doctect.health_list.entity;

import dac2dac.doctect.health_list.dto.request.DrugDto;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String agencyName;
    private LocalDateTime treatDate;

    //* Prescription과 PrescriptionDrug의 양방향 관계 매핑 및 연관관계 편의 메서드
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionDrug> prescriptionDrugList = new ArrayList<>();

    public void addPrescriptionDrug(DrugDto drugDto) {
        PrescriptionDrug prescriptionDrug = PrescriptionDrug.builder()
            .prescription(this)
            .drugName(drugDto.getDrugName())
            .prescriptionCnt(drugDto.getPrescriptionCnt())
            .medicationDays(drugDto.getMedicationDays())
            .build();

        prescriptionDrugList.add(prescriptionDrug);
    }

    @Builder
    public Prescription(User user, String agencyName, LocalDateTime treatDate) {
        this.user = user;
        this.agencyName = agencyName;
        this.treatDate = treatDate;
    }
}
