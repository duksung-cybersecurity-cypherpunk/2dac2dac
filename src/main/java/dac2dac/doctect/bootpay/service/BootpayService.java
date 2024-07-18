package dac2dac.doctect.bootpay.service;

import dac2dac.doctect.bootpay.dto.request.BootpayBiilingKeyDto;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorDto;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.repository.UserRepository;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Subscribe;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BootpayService {

    private final UserRepository userRepository;

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

        try {
            HashMap res = bootpay.getBillingKey(subscribe);
            JSONObject json =  new JSONObject(res);
            System.out.printf( "JSON: %s", json);

            if(res.get("error_code") == null) { //success
                System.out.println("getBillingKey success: " + res);
            } else {
                System.out.println("getBillingKey false: " + res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
