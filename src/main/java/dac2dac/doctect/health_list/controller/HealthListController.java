package dac2dac.doctect.health_list.controller;

import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import dac2dac.doctect.health_list.dto.request.UserAuthenticationDto;
import dac2dac.doctect.health_list.service.HealthListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "내역", description = "HealthList API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mydata")
public class HealthListController {

    private final HealthListService healthListService;

    @Operation(summary = "마이데이터 연동 API", description = "마이데이터 서버로부터 유저의 마이데이터를 연동한다.")
    @PostMapping("/sync/{userId}")
    public ApiResult syncMydata(@Valid @RequestBody UserAuthenticationDto userAuthenticationDto, @PathVariable Long userId) {
        healthListService.syncMydata(userAuthenticationDto, userId);
        return ApiResult.success(SuccessCode.SYNC_SUCCESS);
    }

}
