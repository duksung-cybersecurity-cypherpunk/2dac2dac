package dac2dac.doctect.bootpay.controller;

import dac2dac.doctect.bootpay.dto.request.BootpayBiilingKeyDto;
import dac2dac.doctect.bootpay.service.BootpayService;
import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "결제", description = "결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class BootpayController {

    private final BootpayService bootpayService;

    @Operation(summary = "결제방식 등록 API", description = "부트페이로부터 Billing Key를 발급받아 결제방식을 등록한다.")
    @PostMapping("/billingKey/{userId}")
    public ApiResult subscribeBillingkey(@Valid @RequestBody BootpayBiilingKeyDto bootpayBiilingKeyDto, @PathVariable Long userId) throws Exception {
        bootpayService.subscribeBillingkey(bootpayBiilingKeyDto, userId);
        return ApiResult.success(SuccessCode.CREATED_SUCCESS);
    }
}
