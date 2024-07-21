package dac2dac.doctect.bootpay.controller;

import dac2dac.doctect.bootpay.dto.request.BootpayBiilingKeyDto;
import dac2dac.doctect.bootpay.dto.request.BootpayPayDto;
import dac2dac.doctect.bootpay.service.BootpayService;
import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import dac2dac.doctect.user.entity.PaymentInfo;
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

    @Operation(summary = "결제 API", description = "Billing Key를 이용하여 등록된 결제방식을 통해 결제를 진행한다.")
    @PostMapping("/pay/{userId}")
    public ApiResult subscribeBillingkey(@Valid @RequestBody BootpayPayDto bootpayPayDto, @PathVariable Long userId) throws Exception {
        bootpayService.payWithBillingKey(bootpayPayDto, userId);
        return ApiResult.success(SuccessCode.PAYMENT_SUCCESS);
    }
}
