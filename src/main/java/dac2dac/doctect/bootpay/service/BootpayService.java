package dac2dac.doctect.bootpay.service;

import dac2dac.doctect.bootpay.dto.request.BootpaySubscribeBiilingKeyDto;
import dac2dac.doctect.bootpay.dto.request.BootpayPayDto;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.common.error.exception.UnauthorizedException;
import dac2dac.doctect.bootpay.entity.PaymentInfo;
import dac2dac.doctect.bootpay.entity.PaymentMethod;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.bootpay.entity.constant.ActiveStatus;
import dac2dac.doctect.bootpay.entity.constant.PaymentStatus;
import dac2dac.doctect.bootpay.entity.constant.PaymentType;
import dac2dac.doctect.bootpay.repository.PaymentInfoRepository;
import dac2dac.doctect.bootpay.repository.PaymentMethodRepository;
import dac2dac.doctect.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Subscribe;
import kr.co.bootpay.model.request.SubscribePayload;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.OffsetDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BootpayService {

    private final UserRepository userRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentInfoRepository paymentInfoRepository;

    @Value("${bootpay.application-id}")
    private String applicationId;

    @Value("${bootpay.private-key}")
    private String privateKey;

    private Bootpay bootpay;

    @PostConstruct
    public void init() {
        bootpay = new Bootpay(applicationId, privateKey);
    }

    public void subscribeBillingKey(BootpaySubscribeBiilingKeyDto bootpaySubscribeBiilingKeyDto, Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        bootpay.getAccessToken();

        Subscribe subscribe = new Subscribe();
        subscribe.orderName = "새 결제방식 등록";
        subscribe.subscriptionId = "" + (System.currentTimeMillis() / 1000);
        subscribe.pg = "nicepay";
        subscribe.cardNo = bootpaySubscribeBiilingKeyDto.getCardNo();
        subscribe.cardPw = bootpaySubscribeBiilingKeyDto.getCardPw();
        subscribe.cardExpireYear = bootpaySubscribeBiilingKeyDto.getCardExpireYear();
        subscribe.cardExpireMonth = bootpaySubscribeBiilingKeyDto.getCardExpireMonth();
        subscribe.cardIdentityNo = bootpaySubscribeBiilingKeyDto.getCardIdentityNo();

        HashMap<String, Object> res = bootpay.getBillingKey(subscribe);
        if (res.get("error_code") == null) {
            JSONObject json =  new JSONObject(res);
            HashMap<String, Object> billingData = (HashMap<String, Object>) res.get("billing_data");
            String cardCompany = billingData.get("card_company").toString();
            String cardNo = billingData.get("card_no").toString();
            String lastFourDigits = cardNo.substring(cardNo.length() - 4);

            PaymentMethod paymentMethod = PaymentMethod.builder()
                    .billingKey(json.get("billing_key").toString())
                    .cardCompany(cardCompany)
                    .cardLast4Digits(lastFourDigits)
                    .paymentType(PaymentType.CREDIT_CARD)
                    .status(ActiveStatus.ACTIVE)
                    .user(user)
                    .build();

            paymentMethodRepository.save(paymentMethod);
        } else {
            String errorMessage = res.get("message") != null ? res.get("message").toString() : "Unknown error occurred.";
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }

    public void payWithBillingKey(BootpayPayDto bootpayPayDto, Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByBillingKey(bootpayPayDto.getBillingKey())
                .orElseThrow(() -> new NotFoundException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

        //* 결제 방식이 활성화되어 있는지 확인한다.
        if (paymentMethod.getStatus() == ActiveStatus.INACTIVE) {
            throw new UnauthorizedException(ErrorCode.PAYMENT_METHOD_NOT_FOUND);
        }

        bootpay.getAccessToken();

        SubscribePayload payload = new SubscribePayload();
        payload.billingKey = bootpayPayDto.getBillingKey();
        payload.orderName = bootpayPayDto.getOrderName();
        payload.price = bootpayPayDto.getPrice();
        payload.orderId = "" + (System.currentTimeMillis() / 1000);

        HashMap res = bootpay.requestSubscribe(payload);
        if(res.get("error_code") == null) {
            JSONObject json =  new JSONObject(res);
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(json.get("purchased_at").toString());

            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .price(Integer.valueOf(json.get("price").toString()))
                    .paymentMethod(paymentMethod)
                    .createDate(offsetDateTime.toLocalDateTime())
                    .status(PaymentStatus.COMPLETE)
                    .build();

            paymentInfoRepository.save(paymentInfo);
        } else {
            String errorMessage = res.get("message") != null ? res.get("message").toString() : "Unknown error occurred.";
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }

    public void deleteBillingKey(Long userId, String billingKey) throws Exception {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        bootpay.getAccessToken();

        HashMap res = bootpay.destroyBillingKey(billingKey);
        if(res.get("error_code") == null) {
            //* DB에 등록된 결제 방식 삭제(결제 방식의 status를 ACTIVE -> INACTIVE로 변경)
            PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByBillingKey(billingKey)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

            paymentMethod.deletePayment();
            paymentMethodRepository.save(paymentMethod);
        } else {
            String errorMessage = res.get("message") != null ? res.get("message").toString() : "Unknown error occurred.";
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }
}
