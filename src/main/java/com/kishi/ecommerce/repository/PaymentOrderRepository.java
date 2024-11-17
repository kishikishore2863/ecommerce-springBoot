package com.kishi.ecommerce.repository;

import com.kishi.ecommerce.model.PaymentOder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOder,Long> {

    PaymentOder findByPaymentLinkId(String paymentId);
}
