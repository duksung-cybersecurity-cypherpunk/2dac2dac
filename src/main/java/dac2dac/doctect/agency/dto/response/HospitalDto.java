package dac2dac.doctect.agency.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드 제외
public class HospitalDto {

    private Long id;
    private String name;
    private String tel;
    private String address;
    private Double distance;

    private String thumnail;

    private Integer recentVisitDays;
    private Boolean isFavorite;

    private boolean isOpen;
    private Integer todayOpenTime;
    private Integer todayCloseTime;

    private Double latitude;
    private Double longtitude;



    @Builder
    public HospitalDto(Long id,String name, String tel, String address, Double distance, String thumnail, Integer recentVisitDays, Boolean isFavorite, boolean isOpen, Integer todayOpenTime,
        Integer todayCloseTime, Double latitude, Double longtitude) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.distance = distance;
        this.thumnail = thumnail;
        this.recentVisitDays = recentVisitDays;
        this.isFavorite = isFavorite;
        this.isOpen = isOpen;
        this.todayOpenTime = todayOpenTime;
        this.todayCloseTime = todayCloseTime;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }



}
