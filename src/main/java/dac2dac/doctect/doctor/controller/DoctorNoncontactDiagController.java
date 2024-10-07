package dac2dac.doctect.doctor.controller;

import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import dac2dac.doctect.doctor.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "(의사) 진료 내역", description = "의사 진료 내역 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors/noncontactDiag")
public class DoctorNoncontactDiagController {

    private final DoctorService doctorService;

    @Operation(summary = "진료 완료 내역 조회 API", description = "진료가 완료된 내역을 조회한다.")
    @GetMapping("/completed/{doctorId}")
    public ApiResult getCompletedReservation(@PathVariable Long doctorId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, doctorService.getCompletedReservation(doctorId));
    }

}
