package dac2dac.doctect.health_list.entity;

import dac2dac.doctect.user.entity.User;
import jakarta.persistence.Entity;
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
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String agencyName;

    private String vaccine;
    private Integer vaccSeries;
    private LocalDateTime vaccDate;

    @Builder
    public Vaccination(User user, String agencyName, String vaccine, Integer vaccSeries, LocalDateTime vaccDate) {
        this.user = user;
        this.agencyName = agencyName;
        this.vaccine = vaccine;
        this.vaccSeries = vaccSeries;
        this.vaccDate = vaccDate;
    }
}
