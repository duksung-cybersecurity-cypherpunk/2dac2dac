package dac2dac.doctect.agency.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Agency {

    public Integer diagTimeMonOpen;
    protected Integer diagTimeMonClose;

    protected Integer diagTimeTuesOpen;
    protected Integer diagTimeTuesClose;

    protected Integer diagTimeWedsOpen;
    protected Integer diagTimeWedsClose;

    protected Integer diagTimeThursOpen;
    protected Integer diagTimeThursClose;

    protected Integer diagTimeFriOpen;
    protected Integer diagTimeFriClose;

    protected Integer diagTimeSatOpen;
    protected Integer diagTimeSatClose;

    protected Integer diagTimeSunOpen;
    protected Integer diagTimeSunClose;

    protected Integer diagTimeHolidayOpen;
    protected Integer diagTimeHolidayClose;

}
