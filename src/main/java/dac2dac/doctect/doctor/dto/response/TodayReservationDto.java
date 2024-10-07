package dac2dac.doctect.doctor.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodayReservationDto {

    private int totalCnt;
    private List<ReservationItem> completedReservationItemList = new ArrayList<>();
    private List<ReservationItem> scheduledReservationItemList = new ArrayList<>();

    @Builder
    public TodayReservationDto(int totalCnt, List<ReservationItem> completedReservationItemList, List<ReservationItem> scheduledReservationItemList) {
        this.totalCnt = totalCnt;
        this.completedReservationItemList = completedReservationItemList;
        this.scheduledReservationItemList = scheduledReservationItemList;
    }
}
