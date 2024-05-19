package dac2dac.doctect.health_list.entity;

import dac2dac.doctect.health_list.entity.constant.DiagType;
import dac2dac.doctect.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactDiag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String agencyName;
    private LocalDateTime diagDate;

    @Enumerated(EnumType.STRING)
    private DiagType diagType;

    private Integer prescription_cnt;
    private Integer medication_cnt;
    private Integer visit_days;

    @Builder
    public ContactDiag(User user, String agencyName, LocalDateTime diagDate, DiagType diagType, Integer prescription_cnt, Integer medication_cnt, Integer visit_days) {
        this.user = user;
        this.agencyName = agencyName;
        this.diagDate = diagDate;
        this.diagType = diagType;
        this.prescription_cnt = prescription_cnt;
        this.medication_cnt = medication_cnt;
        this.visit_days = visit_days;
    }
}
