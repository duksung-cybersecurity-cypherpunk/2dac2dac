package dac2dac.doctect.health_list.dto.request;

import dac2dac.doctect.health_list.entity.Vaccination;
import dac2dac.doctect.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VaccinationDto {

    private String agencyName;
    private String vaccine;
    private Integer vaccSeries;
    private LocalDateTime vaccDate;

    @Builder
    public VaccinationDto(String agencyName, String vaccine, Integer vaccSeries, LocalDateTime vaccDate) {
        this.agencyName = agencyName;
        this.vaccine = vaccine;
        this.vaccSeries = vaccSeries;
        this.vaccDate = vaccDate;
    }

    public Vaccination toEntity(User user) {
        return Vaccination.builder()
            .user(user)
            .agencyName(agencyName)
            .vaccine(vaccine)
            .vaccSeries(vaccSeries)
            .vaccDate(vaccDate)
            .build();
    }
}
