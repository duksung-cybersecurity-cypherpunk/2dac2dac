package dac2dac.doctect.user.controller;

import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저", description = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Operation(summary = "test hello", description = "hello api example")
    @GetMapping("/test")
    public ApiResult test() {
        String data = "test";
//        throw new NotFoundException(ErrorCode.ENTITY_NOT_FOUND);
        return ApiResult.success(SuccessCode.GET_SUCCESS, data);
    }

}
