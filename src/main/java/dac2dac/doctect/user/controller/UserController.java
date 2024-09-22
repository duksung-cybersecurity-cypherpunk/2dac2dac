package dac2dac.doctect.user.controller;

import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import dac2dac.doctect.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저", description = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "test hello", description = "hello api example")
    @GetMapping("/test")
    public ApiResult test() {
        String data = "test";
//        throw new NotFoundException(ErrorCode.ENTITY_NOT_FOUND);
        return ApiResult.success(SuccessCode.GET_SUCCESS, data);
    }

    @Operation(summary = "유저 정보 조회 API", description = "마이페이지에 보여지는 유저의 정보를 조회한다.")
    @GetMapping("/{userId}")
    public ApiResult getUserInfo(@PathVariable Long userId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, userService.getUserInfo(userId));
    }

    @Operation(summary = "다가오는 진료 예약 조회 API", description = "메인에 보여지는 다가오는 환자의 진료 예약 정보를 조회한다.")
    @GetMapping("/{userId}/reservation/upcoming")
    public ApiResult getUpcomingReservation(@PathVariable Long userId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, userService.getUpcomingReservation(userId));
    }

}
