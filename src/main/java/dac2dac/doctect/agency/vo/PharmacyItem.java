package dac2dac.doctect.agency.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dac2dac.doctect.agency.entity.Pharmacy;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PharmacyItem {

    @JsonProperty("dutyName")
    private String name;

    @JsonProperty("dutyAddr")
    private String address; // 이 부분을 추가해줍니다.

    @JsonProperty("dutyFax")
    private String fax;

    @JsonProperty("dutyTel1")
    private String tel;

    @JsonProperty("wgs84Lat")
    private Double latitude;

    @JsonProperty("wgs84Lon")
    private Double longitude;

    @JsonProperty("dutyTime1s")
    private Integer diagTimeMonOpen;

    @JsonProperty("dutyTime1c")
    private Integer diagTimeMonClose;

    @JsonProperty("dutyTime2s")
    private Integer diagTimeTuesOpen;

    @JsonProperty("dutyTime2c")
    private Integer diagTimeTuesClose;

    @JsonProperty("dutyTime3s")
    private Integer diagTimeWedsOpen;

    @JsonProperty("dutyTime3c")
    private Integer diagTimeWedsClose;

    @JsonProperty("dutyTime4s")
    private Integer diagTimeThursOpen;

    @JsonProperty("dutyTime4c")
    private Integer diagTimeThursClose;

    @JsonProperty("dutyTime5s")
    private Integer diagTimeFriOpen;

    @JsonProperty("dutyTime5c")
    private Integer diagTimeFriClose;

    @JsonProperty("dutyTime6s")
    private Integer diagTimeSatOpen;

    @JsonProperty("dutyTime6c")
    private Integer diagTimeSatClose;

    @JsonProperty("dutyTime7s")
    private Integer diagTimeSunOpen;

    @JsonProperty("dutyTime7c")
    private Integer diagTimeSunClose;

    @JsonProperty("dutyTime8s")
    private Integer diagTimeHolidayOpen;

    @JsonProperty("dutyTime8c")
    private Integer diagTimeHolidayClose;

    public Pharmacy toEntity() {
        return Pharmacy.builder()
            .name(name)
            .address(address)
            .tel(tel)
            .fax(fax)
            .longitude(longitude)
            .latitude(latitude)
            .diagTimeMonOpen(diagTimeMonOpen)
            .diagTimeMonClose(diagTimeMonClose)
            .diagTimeTuesOpen(diagTimeTuesOpen)
            .diagTimeTuesClose(diagTimeTuesClose)
            .diagTimeWedsOpen(diagTimeWedsOpen)
            .diagTimeWedsClose(diagTimeWedsClose)
            .diagTimeThursOpen(diagTimeThursOpen)
            .diagTimeThursClose(diagTimeThursClose)
            .diagTimeFriOpen(diagTimeFriOpen)
            .diagTimeFriClose(diagTimeFriClose)
            .diagTimeSatOpen(diagTimeSatOpen)
            .diagTimeSatClose(diagTimeSatClose)
            .diagTimeSunOpen(diagTimeSunOpen)
            .diagTimeSunClose(diagTimeSunClose)
            .diagTimeHolidayOpen(diagTimeHolidayOpen)
            .diagTimeHolidayClose(diagTimeHolidayClose)
            .build();
    }

}
