package dac2dac.doctect.health_list.dto.response.healthScreening;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthScreeningItem {

    private Long hsId;

    private LocalDateTime diagDate;

    private String doctorName;
    private String doctorHospital;

    @Builder
    public HealthScreeningItem(Long hsId, LocalDateTime diagDate, String doctorName, String doctorHospital) {
        this.hsId = hsId;
        this.diagDate = diagDate;
        this.doctorName = doctorName;
        this.doctorHospital = doctorHospital;
    }
}
