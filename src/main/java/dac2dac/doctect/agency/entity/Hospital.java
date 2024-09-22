package dac2dac.doctect.agency.entity;

import dac2dac.doctect.agency.entity.constant.AgencyType;
import dac2dac.doctect.common.entity.DiagTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital extends Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagDiv;
    private boolean isErOperate;

    private String hpid;

    @Builder
    public Hospital(Long id, String name, String address, String tel, String thumnail, String diagDiv, Boolean isErOperate, Double longitude, Double latitude, String hpid, Integer diagTimeMonOpen,
                    Integer diagTimeMonClose,
                    Integer diagTimeTuesOpen, Integer diagTimeTuesClose, Integer diagTimeWedsOpen, Integer diagTimeWedsClose, Integer diagTimeThursOpen, Integer diagTimeThursClose, Integer diagTimeFriOpen,
                    Integer diagTimeFriClose, Integer diagTimeSatOpen, Integer diagTimeSatClose, Integer diagTimeSunOpen, Integer diagTimeSunClose, Integer diagTimeHolidayOpen, Integer diagTimeHolidayClose) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.thumnail = thumnail;
        this.diagDiv = diagDiv;
        this.isErOperate = isErOperate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hpid = hpid;
        this.diagTime = new DiagTime(diagTimeMonOpen, diagTimeMonClose, diagTimeTuesOpen, diagTimeTuesClose, diagTimeWedsOpen, diagTimeWedsClose,
                diagTimeThursOpen, diagTimeThursClose, diagTimeFriOpen, diagTimeFriClose, diagTimeSatOpen, diagTimeSatClose,
                diagTimeSunOpen, diagTimeSunClose, diagTimeHolidayOpen, diagTimeHolidayClose);

    }

    @Override
    public AgencyType getAgencyType() {
        return AgencyType.HOSPITAL;
    }

    @Override
    public Long getId() {
        return id;
    }

}
