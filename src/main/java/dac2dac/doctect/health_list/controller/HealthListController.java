package dac2dac.doctect.health_list.controller;

import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import dac2dac.doctect.health_list.dto.request.UserAuthenticationDto;
import dac2dac.doctect.health_list.service.HealthListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "내역", description = "HealthList API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/healthList")
public class HealthListController {

    private final HealthListService healthListService;

    @Operation(summary = "마이데이터 연동 API", description = "마이데이터 서버로부터 유저의 마이데이터를 연동한다.")
    @PostMapping("/sync/{userId}")
    public ApiResult syncMydata(@Valid @RequestBody UserAuthenticationDto userAuthenticationDto, @PathVariable Long userId) {
        healthListService.syncMydata(userAuthenticationDto, userId);
        return ApiResult.success(SuccessCode.SYNC_SUCCESS);
    }

    @Operation(summary = "진료 내역(대면) 상세 조회 API", description = "유저의 진 내역(대면)을 상세 조회한다.")
    @GetMapping("{userId}/diagnosis/contact/{diagId}")
    public ApiResult getContactDiagnosis(@PathVariable Long userId, @PathVariable Long diagId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, healthListService.getDetailContactDiagnosis(userId, diagId));
    }
}
