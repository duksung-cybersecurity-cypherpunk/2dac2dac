package dac2dac.doctect.doctor.controller;

import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import dac2dac.doctect.doctor.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예약", description = "의사 예약 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "날짜별 예약 조회 API", description = "날짜별 요청된 예약과 수락된 예약을 조회한다.")
    @PostMapping("/{doctorId}/{reservationDate}")
    public ApiResult getReservations(@PathVariable Long doctorId, @PathVariable("reservationDate") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String reservationDate) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, doctorService.getReservations(doctorId, reservationDate));
    }

    @Operation(summary = "예약 신청서 조회 API", description = "예약 신청서를 조회한다.")
    @PostMapping("/form/{doctorId}/{reservationId}")
    public ApiResult getReservations(@PathVariable Long doctorId, @PathVariable Long reservationId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, doctorService.getReservationForm(doctorId, reservationId));
    }
}
