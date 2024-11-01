package dac2dac.doctect.doctor.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodayScheduledReservationDto {

    private int totalCnt;
    private List<ReservationItem> scheduledReservationItemList = new ArrayList<>();

    @Builder
    public TodayScheduledReservationDto(int totalCnt, List<ReservationItem> scheduledReservationItemList) {
        this.totalCnt = totalCnt;
        this.scheduledReservationItemList = scheduledReservationItemList;
    }
}
