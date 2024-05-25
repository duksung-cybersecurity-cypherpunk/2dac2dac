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
}
