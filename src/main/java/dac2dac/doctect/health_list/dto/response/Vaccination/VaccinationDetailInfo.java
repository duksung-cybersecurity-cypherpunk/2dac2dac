package dac2dac.doctect.health_list.dto.response.Vaccination;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VaccinationDetailInfo {

    private LocalDateTime vaccDate;
    private String vaccName;
    private Integer vaccSeries;
    private Long postVaccinationDays;

    @Builder
    public VaccinationDetailInfo(LocalDateTime vaccDate, String vaccName, Integer vaccSeries, Long postVaccinationDays) {
        this.vaccDate = vaccDate;
        this.vaccName = vaccName;
        this.vaccSeries = vaccSeries;
        this.postVaccinationDays = postVaccinationDays;
    }
}
