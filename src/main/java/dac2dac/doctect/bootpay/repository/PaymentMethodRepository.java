package dac2dac.doctect.bootpay.repository;

import dac2dac.doctect.bootpay.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    Optional<PaymentMethod> findPaymentMethodByBillingKey(String billingKey);
}
