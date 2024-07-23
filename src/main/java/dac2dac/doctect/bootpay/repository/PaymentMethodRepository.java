package dac2dac.doctect.bootpay.repository;

import dac2dac.doctect.bootpay.entity.PaymentMethod;
import dac2dac.doctect.bootpay.entity.constant.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    Optional<PaymentMethod> findPaymentMethodByBillingKey(String billingKey);

    List<PaymentMethod> findPaymentMethodByUserIdAndStatus(Long userId, ActiveStatus activeStatus);
}
