package dac2dac.doctect.health_list.dto.response.healthScreening;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthScreeningInfo {

    private LocalDateTime diagDate;

    private String doctorName;
    private String doctorHospital;

    private String opinion;

    @Builder
    public HealthScreeningInfo(LocalDateTime diagDate, String doctorName, String doctorHospital, String opinion) {
        this.diagDate = diagDate;
        this.doctorName = doctorName;
        this.doctorHospital = doctorHospital;
        this.opinion = opinion;
    }
}
