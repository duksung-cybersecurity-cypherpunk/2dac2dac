package dac2dac.doctect.agency.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String tel;

    private Double longitude;
    private Double latitude;

    private String diagTimeMon;
    private String diagTimeTues;
    private String diagTimeWeds;
    private String diagTimeThurs;
    private String diagTimeFri;
    private String diagTimeSat;
    private String diagTimeSun;

}
