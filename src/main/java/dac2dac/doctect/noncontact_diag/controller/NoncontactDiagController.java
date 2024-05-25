package dac2dac.doctect.noncontact_diag.controller;

import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import dac2dac.doctect.noncontact_diag.service.NoncontactDiagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "비대면진료", description = "비대면진료 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/noncontactDiag")
public class NoncontactDiagController {

    private final NoncontactDiagService noncontactDiagService;

    @Operation(summary = "진료과목 리스트 조회 API", description = "진료 과목 리스트를 조회한다.")
    @GetMapping("/departments")
    public ApiResult getDepartmentList() {
        return ApiResult.success(SuccessCode.GET_SUCCESS, noncontactDiagService.getDepartmentList());
    }

    @Operation(summary = "진료과목 별 의사 리스트 조회 API", description = "진료 과목 별 의사 리스트를 조회한다.")
    @GetMapping("/departments/{departmentId}/doctors")
    public ApiResult getDoctorList(@PathVariable Long departmentId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, noncontactDiagService.getDoctorList(departmentId));
    }

}
