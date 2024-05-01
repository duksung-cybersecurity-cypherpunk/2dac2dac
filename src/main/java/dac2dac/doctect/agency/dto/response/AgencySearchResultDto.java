package dac2dac.doctect.agency.dto.response;

import dac2dac.doctect.agency.entity.constant.AgencyType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AgencySearchResultDto {

    private String name;

    private boolean isOpen;
    private Integer todayOpenTime;
    private Integer todayCloseTime;

    private Double distance;
    private String address;
    private String tel;

    private Double latitude;
    private Double longtitude;

    private AgencyType agencyType;

    @Builder
    public AgencySearchResultDto(String name, boolean isOpen, Integer todayOpenTime, Integer todayCloseTime, Double distance, String address, String tel, Double latitude, Double longtitude,
        AgencyType agencyType) {
        this.name = name;
        this.isOpen = isOpen;
        this.todayOpenTime = todayOpenTime;
        this.todayCloseTime = todayCloseTime;
        this.distance = distance;
        this.address = address;
        this.tel = tel;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.agencyType = agencyType;
    }

}
