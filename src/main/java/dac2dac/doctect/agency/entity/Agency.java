package dac2dac.doctect.agency.entity;

import dac2dac.doctect.agency.entity.constant.AgencyType;
import dac2dac.doctect.common.entity.DiagTime;
import jakarta.persistence.Embedded;
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

    protected String name;
    protected String address;
    protected String tel;

    protected Double longitude;
    protected Double latitude;

    @Embedded
    protected DiagTime diagTime;

    protected String thumnail;

    public abstract AgencyType getAgencyType();

    public abstract Long getId();

}
