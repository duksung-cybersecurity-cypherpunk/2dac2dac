package dac2dac.doctect.doctor.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationListDto {

    private RequestReservationItemList requestReservationList;
    private AcceptedReservationItemList acceptedReservationList;

    @Builder
    public ReservationListDto(RequestReservationItemList requestReservationList, AcceptedReservationItemList acceptedReservationList) {
        this.requestReservationList = requestReservationList;
        this.acceptedReservationList = acceptedReservationList;
    }
}
