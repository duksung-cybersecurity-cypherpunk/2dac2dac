package dac2dac.doctect.bootpay.service;

import dac2dac.doctect.bootpay.dto.request.BootpayBiilingKeyDto;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.common.error.exception.UnauthorizedException;
import dac2dac.doctect.user.entity.PaymentMethod;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.entity.constant.PaymentType;
import dac2dac.doctect.user.repository.PaymentMethodRepository;
import dac2dac.doctect.user.repository.UserRepository;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Subscribe;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BootpayService {

    private final UserRepository userRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Value("${bootpay.application-id}")
    private String applicationId;

    @Value("${bootpay.private-key}")
    private String privateKey;

    public void subscribeBillingkey(BootpayBiilingKeyDto bootpayBiilingKeyDto, Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Bootpay bootpay = new Bootpay(applicationId, privateKey);
        bootpay.getAccessToken();

        Subscribe subscribe = new Subscribe();
        subscribe.orderName = "새 결제방식 등록";
        subscribe.subscriptionId = "" + (System.currentTimeMillis() / 1000);
        subscribe.pg = "nicepay";
        subscribe.cardNo = bootpayBiilingKeyDto.getCardNo();
        subscribe.cardPw = bootpayBiilingKeyDto.getCardPw();
        subscribe.cardExpireYear = bootpayBiilingKeyDto.getCardExpireYear();
        subscribe.cardExpireMonth = bootpayBiilingKeyDto.getCardExpireMonth();
        subscribe.cardIdentityNo = bootpayBiilingKeyDto.getCardIdentityNo();

        HashMap<String, Object> res = bootpay.getBillingKey(subscribe);
        JSONObject json =  new JSONObject(res);

        if (res.get("error_code") == null) {
            HashMap<String, Object> billingData = (HashMap<String, Object>) res.get("billing_data");
            String cardCompany = billingData.get("card_company").toString();
            String cardNo = billingData.get("card_no").toString();
            String lastFourDigits = cardNo.substring(cardNo.length() - 4);
            PaymentMethod paymentMethod = PaymentMethod.builder()
                    .billingKey(json.get("billing_key").toString())
                    .cardCompany(cardCompany)
                    .cardLast4Digits(lastFourDigits)
                    .paymentType(PaymentType.CREDIT_CARD)
                    .user(user)
                    .build();

            paymentMethodRepository.save(paymentMethod);
        } else {
            String errorMessage = res.get("message") != null ? res.get("message").toString() : "Unknown error occurred.";
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }
}
