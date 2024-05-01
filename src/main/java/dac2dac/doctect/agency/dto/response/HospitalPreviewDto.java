package dac2dac.doctect.agency.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HospitalPreviewDto {

    private String name;

    private boolean isOpen;
    private Integer todayOpenTime;
    private Integer todayCloseTime;

    private Double distance;

    private Double latitude;
    private Double longtitude;

    @Builder
    public HospitalPreviewDto(String name, boolean isOpen, Integer todayOpenTime, Integer todayCloseTime, Double distance, Double latitude, Double longtitude) {
        this.name = name;
        this.isOpen = isOpen;
        this.todayOpenTime = todayOpenTime;
        this.todayCloseTime = todayCloseTime;
        this.distance = distance;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
}
