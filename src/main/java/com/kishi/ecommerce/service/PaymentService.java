package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Order;
import com.kishi.ecommerce.model.PaymentOder;
import com.kishi.ecommerce.model.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

import java.util.Set;

public interface PaymentService {

    PaymentOder createOrder(User user, Set<Order> orders);

    PaymentOder getPaymentOrderById(Long orderId) throws Exception;

    PaymentOder getPaymentOrderByPaymentId(String orderId) throws Exception;

    Boolean proceedPaymentOrder(PaymentOder paymentOder,String paymentId,String paymentLinkId) throws RazorpayException;

    PaymentLink createRazorpayPaymentLink(User user ,Long amount ,Long orderId) throws RazorpayException;

    String createStripePaymentLink(User user,Long amount,Long orderId) throws StripeException;
}
