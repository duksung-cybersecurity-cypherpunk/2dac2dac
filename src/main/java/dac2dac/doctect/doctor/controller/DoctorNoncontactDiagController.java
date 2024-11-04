package dac2dac.doctect.doctor.controller;

import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import dac2dac.doctect.doctor.dto.request.SearchMedicineRequestDto;
import dac2dac.doctect.doctor.service.DoctorNoncontactDiagService;
import dac2dac.doctect.doctor.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "(의사) 진료 내역", description = "의사 진료 내역 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors/noncontactDiag")
public class DoctorNoncontactDiagController {

    private final DoctorNoncontactDiagService doctorService;
    private final MedicineService medicineService;

    @Operation(summary = "진료 완료 내역 조회 API", description = "진료가 완료된 내역을 조회한다.")
    @GetMapping("/completed/{doctorId}")
    public ApiResult getCompletedReservation(@PathVariable Long doctorId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, doctorService.getCompletedReservation(doctorId));
    }

    @Operation(summary = "처방전 조회 API", description = "처방전을 조회한다.")
    @GetMapping("/prescription/{noncontactDiagId}")
    public ApiResult getPrescription(@PathVariable Long noncontactDiagId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, doctorService.getPrescription(noncontactDiagId));
    }

    @Operation(summary = "의약품 검색 API", description = "의약품 정보를 검색한다.")
    @PostMapping("/medicines")
    public ApiResult searchMedicine(@Valid @RequestBody SearchMedicineRequestDto request) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, medicineService.searchMedicine(request));
    }

}
