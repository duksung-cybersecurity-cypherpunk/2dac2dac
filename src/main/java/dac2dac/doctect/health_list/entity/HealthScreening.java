package dac2dac.doctect.health_list.entity;

import dac2dac.doctect.user.entity.User;
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

    @OneToOne
    @JoinColumn(name = "measurement_test_id")
    private MeasurementTest measurementTest;

    @OneToOne
    @JoinColumn(name = "blood_test_id")
    private BloodTest bloodTest;

    @OneToOne
    @JoinColumn(name = "other_test_id")
    private OtherTest otherTest;

    private String agencyName;
    private LocalDateTime checkupDate;

    @Lob
    private String opinion;

}
