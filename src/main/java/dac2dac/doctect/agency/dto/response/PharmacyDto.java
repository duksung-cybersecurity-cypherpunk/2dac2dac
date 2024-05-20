package dac2dac.doctect.agency.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PharmacyDto {

    private String name;
    private String tel;
    private String address;
    private Double distance;

    private String thumnail;

    private Boolean isFavorite;

    private boolean isOpen;
    private Integer todayOpenTime;
    private Integer todayCloseTime;

    private Double latitude;
    private Double longtitude;

    @Builder
    public PharmacyDto(String name, String tel, String address, Double distance, String thumnail, Boolean isFavorite, boolean isOpen, Integer todayOpenTime, Integer todayCloseTime, Double latitude,
        Double longtitude) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.distance = distance;
        this.thumnail = thumnail;
        this.isFavorite = isFavorite;
        this.isOpen = isOpen;
        this.todayOpenTime = todayOpenTime;
        this.todayCloseTime = todayCloseTime;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
}
