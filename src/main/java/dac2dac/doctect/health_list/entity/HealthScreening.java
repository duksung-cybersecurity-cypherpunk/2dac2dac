package dac2dac.doctect.health_list.entity;

import dac2dac.doctect.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthScreening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String agencyName;
    private String doctorName;
    private LocalDateTime checkupDate;

    @Lob
    private String opinion;

    @OneToOne(mappedBy = "healthScreening", cascade = CascadeType.ALL, orphanRemoval = true)
    private MeasurementTest measurementTest;

    @OneToOne(mappedBy = "healthScreening", cascade = CascadeType.ALL, orphanRemoval = true)
    private BloodTest bloodTest;

    @OneToOne(mappedBy = "healthScreening", cascade = CascadeType.ALL, orphanRemoval = true)
    private OtherTest otherTest;

    // 연관관계 편의 메서드
    public void setMeasurementTest(MeasurementTest measurementTest) {
        this.measurementTest = measurementTest;
        measurementTest.setHealthScreening(this);
    }

    public void setBloodTest(BloodTest bloodTest) {
        this.bloodTest = bloodTest;
        bloodTest.setHealthScreening(this);
    }

    public void setOtherTest(OtherTest otherTest) {
        this.otherTest = otherTest;
        otherTest.setHealthScreening(this);
    }

    @Builder
    public HealthScreening(User user, String agencyName, String doctorName, LocalDateTime checkupDate, String opinion, MeasurementTest measurementTest, BloodTest bloodTest, OtherTest otherTest) {
        this.user = user;
        this.agencyName = agencyName;
        this.doctorName = doctorName;
        this.checkupDate = checkupDate;
        this.opinion = opinion;
        this.measurementTest = measurementTest;
        this.bloodTest = bloodTest;
        this.otherTest = otherTest;
    }
}
