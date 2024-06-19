package dac2dac.doctect.common.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class DiagTime {

    private Integer diagTimeMonOpen;
    private Integer diagTimeMonClose;

    private Integer diagTimeTuesOpen;
    private Integer diagTimeTuesClose;

    private Integer diagTimeWedsOpen;
    private Integer diagTimeWedsClose;

    private Integer diagTimeThursOpen;
    private Integer diagTimeThursClose;

    private Integer diagTimeFriOpen;
    private Integer diagTimeFriClose;

    private Integer diagTimeSatOpen;
    private Integer diagTimeSatClose;

    private Integer diagTimeSunOpen;
    private Integer diagTimeSunClose;

    private Integer diagTimeHolidayOpen;
    private Integer diagTimeHolidayClose;

    public DiagTime(Integer diagTimeMonOpen, Integer diagTimeMonClose, Integer diagTimeTuesOpen, Integer diagTimeTuesClose, Integer diagTimeWedsOpen, Integer diagTimeWedsClose,
        Integer diagTimeThursOpen,
        Integer diagTimeThursClose, Integer diagTimeFriOpen, Integer diagTimeFriClose, Integer diagTimeSatOpen, Integer diagTimeSatClose, Integer diagTimeSunOpen, Integer diagTimeSunClose,
        Integer diagTimeHolidayOpen, Integer diagTimeHolidayClose) {
        this.diagTimeMonOpen = diagTimeMonOpen;
        this.diagTimeMonClose = diagTimeMonClose;
        this.diagTimeTuesOpen = diagTimeTuesOpen;
        this.diagTimeTuesClose = diagTimeTuesClose;
        this.diagTimeWedsOpen = diagTimeWedsOpen;
        this.diagTimeWedsClose = diagTimeWedsClose;
        this.diagTimeThursOpen = diagTimeThursOpen;
        this.diagTimeThursClose = diagTimeThursClose;
        this.diagTimeFriOpen = diagTimeFriOpen;
        this.diagTimeFriClose = diagTimeFriClose;
        this.diagTimeSatOpen = diagTimeSatOpen;
        this.diagTimeSatClose = diagTimeSatClose;
        this.diagTimeSunOpen = diagTimeSunOpen;
        this.diagTimeSunClose = diagTimeSunClose;
        this.diagTimeHolidayOpen = diagTimeHolidayOpen;
        this.diagTimeHolidayClose = diagTimeHolidayClose;
    }
}
