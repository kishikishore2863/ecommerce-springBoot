package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.domain.PaymentOrderStatus;
import com.kishi.ecommerce.domain.PaymentStatus;
import com.kishi.ecommerce.model.Order;
import com.kishi.ecommerce.model.PaymentOder;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.repository.OrderRepository;
import com.kishi.ecommerce.repository.PaymentOrderRepository;
import com.kishi.ecommerce.service.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private String apiKey = "apiKey";
    private String apiSeceret="apiSceret";

    private String stripeSecretkey="stripeSecretkey";


    @Override
    public PaymentOder createOrder(User user, Set<Order> orders) {
        Long amount = orders.stream().mapToLong(Order::getTotalSellingPrice).sum();
        PaymentOder paymentOder =new PaymentOder();
        paymentOder.setAmount(amount);
        paymentOder.setUser(user);
        paymentOder.setOrders(orders);
        return paymentOrderRepository.save(paymentOder);
    }

    @Override
    public PaymentOder getPaymentOrderById(Long orderId) throws Exception {
        return paymentOrderRepository.findById(orderId).orElseThrow(()->new Exception("payment order not found"));
    }

    @Override
    public PaymentOder getPaymentOrderByPaymentId(String orderId) throws Exception {
        PaymentOder paymentOder =paymentOrderRepository.findByPaymentLinkId(orderId);
        if (paymentOder == null){
            throw new Exception("payment order not found with provided payment link id");
        }
        return paymentOder;
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOder paymentOder, String paymentId, String paymentLinkId) throws RazorpayException {
        if (paymentOder.getStatus().equals(PaymentOrderStatus.PENDING)){
            RazorpayClient razorpayClient =new RazorpayClient(apiKey , apiSeceret);

            Payment payment = razorpayClient.payments.fetch(paymentId);
            String status = payment.get("status");
            if (status.equals("captured")){
                Set<Order> orders=paymentOder.getOrders();
                for (Order order:orders){
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                    orderRepository.save(order);
                }
                paymentOder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOder);
                return true;
            }
            paymentOder.setStatus(PaymentOrderStatus.FAILED);
            paymentOrderRepository.save(paymentOder);
            return false;
        }
        return false;
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException {

        amount = amount*100;

        try{
            RazorpayClient razorpayClient =new RazorpayClient(apiKey , apiSeceret);

            JSONObject paymentLinkRequest =new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer =new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);
            paymentLinkRequest.put("callback_url","http://localhost:3000/payment-succes/"+orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink paymentLink= razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkUrl = paymentLink.get("short_url");
            String paymentLinkId = paymentLink.get("id");

            return paymentLink;

        }catch (Exception e){
            System.out.println(e.getMessage());
            throw  new RazorpayException(e.getMessage());
        }
    }

    @Override
    public String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey=stripeSecretkey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-succes/"+orderId)
                .setCancelUrl("http://localhost:3000/payment-cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmount(amount*100)
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName("Kishi's shop payment").build()
                                        ).build()
                                ).build()
                        ).build();
        Session session = Session.create(params);

        return session.getUrl();
    }

}
