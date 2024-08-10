package dac2dac.doctect.noncontact_diag.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorInfo {

    private String name;
    private String hospitalName;

    private String profilePath;

    private Boolean isOpen;
    private Integer todayOpenTime;
    private Integer todayCloseTime;

    @Builder
    public DoctorInfo(String name, String hospitalName, String profilePath, Boolean isOpen, Integer todayOpenTime, Integer todayCloseTime) {
        this.name = name;
        this.hospitalName = hospitalName;
        this.profilePath = profilePath;
        this.isOpen = isOpen;
        this.todayOpenTime = todayOpenTime;
        this.todayCloseTime = todayCloseTime;
    }
}
