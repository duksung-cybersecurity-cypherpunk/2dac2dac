package dac2dac.doctect.health_list.dto.request;

import dac2dac.doctect.health_list.entity.HealthScreening;
import dac2dac.doctect.user.entity.User;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthScreeningDto {

    private MeasurementTestDto measurementTest;
    private BloodTestDto bloodTest;
    private OtherTestDto otherTest;

    private String agencyName;
    private LocalDateTime checkupDate;

    @Lob
    private String opinion;

    @Builder
    public HealthScreeningDto(MeasurementTestDto measurementTest, BloodTestDto bloodTest, OtherTestDto otherTest, String agencyName, LocalDateTime checkupDate, String opinion) {
        this.measurementTest = measurementTest;
        this.bloodTest = bloodTest;
        this.otherTest = otherTest;
        this.agencyName = agencyName;
        this.checkupDate = checkupDate;
        this.opinion = opinion;
    }

    public HealthScreening toEntity(User user) {
        return HealthScreening.builder()
            .user(user)
            .agencyName(agencyName)
            .checkupDate(checkupDate)
            .opinion(opinion)
            .build();
    }
}
