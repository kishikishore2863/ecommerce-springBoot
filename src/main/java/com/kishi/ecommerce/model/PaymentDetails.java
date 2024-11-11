package com.kishi.ecommerce.model;

import com.kishi.ecommerce.domain.PaymentStatus;
import lombok.Data;

@Data


public class PaymentDetails {

    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceid;
    private String razorpayPaymentLinkStatus;
    private String razorPaymentIdZWSP;
    private PaymentStatus status;

}
