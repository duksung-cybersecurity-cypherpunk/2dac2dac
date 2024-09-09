package dac2dac.doctect.bootpay.controller;

import dac2dac.doctect.bootpay.dto.request.BootpayPayDto;
import dac2dac.doctect.bootpay.dto.request.BootpaySubscribeBiilingKeyWithSecureKeypadDto;
import dac2dac.doctect.bootpay.service.BootpayService;
import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결제", description = "결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class BootpayController {

    private final BootpayService bootpayService;

    @Operation(summary = "결제방식 등록 API", description = "부트페이로부터 Billing Key를 발급받아 결제방식을 등록한다.")
    @PostMapping("/billingKey/{userId}")
    public ApiResult subscribeBillingKey(@Valid @RequestBody BootpaySubscribeBiilingKeyWithSecureKeypadDto bootpaySubscribeBiilingKeyDto, @PathVariable Long userId) throws Exception {
        bootpayService.subscribeBillingKey(bootpaySubscribeBiilingKeyDto, userId);
        return ApiResult.success(SuccessCode.CREATED_SUCCESS);
    }

    @Operation(summary = "결제 API", description = "Billing Key를 이용하여 등록된 결제방식을 통해 결제를 진행한다.")
    @PostMapping("/pay/{userId}")
    public ApiResult payWithBillingkey(@Valid @RequestBody BootpayPayDto bootpayPayDto, @PathVariable Long userId) throws Exception {
        bootpayService.payWithBillingKey(bootpayPayDto, userId);
        return ApiResult.success(SuccessCode.PAYMENT_SUCCESS);
    }

    @Operation(summary = "결제방식 삭제 API", description = "등록된 결제 방식을 삭제한다. 이후 해당 결제 방식에 대한 Billing Key는 사용할 수 없다.")
    @DeleteMapping("/billingKey/{userId}/{billingKey}")
    public ApiResult deleteBillingkey(@PathVariable Long userId, @PathVariable String billingKey) throws Exception {
        bootpayService.deleteBillingKey(userId, billingKey);
        return ApiResult.success(SuccessCode.DELETE_SUCCESS);
    }

    @Operation(summary = "결제방식 조회 API", description = "등록된 유저의 결제 방식을 모두 조회한다.")
    @GetMapping("/billingKey/{userId}")
    public ApiResult getPaymentMethods(@PathVariable Long userId) {
        return ApiResult.success(SuccessCode.GET_SUCCESS, bootpayService.getPaymentMethods(userId));
    }


}
