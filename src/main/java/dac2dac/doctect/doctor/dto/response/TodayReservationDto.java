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
    private List<ReservationItem> reservationItemList = new ArrayList<>();

    @Builder
    public TodayReservationDto(int totalCnt, List<ReservationItem> reservationItemList) {
        this.totalCnt = totalCnt;
        this.reservationItemList = reservationItemList;
    }
}
