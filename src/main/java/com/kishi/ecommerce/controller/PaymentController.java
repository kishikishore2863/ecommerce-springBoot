package com.kishi.ecommerce.controller;

import com.kishi.ecommerce.model.*;
import com.kishi.ecommerce.response.ApiResponse;
import com.kishi.ecommerce.response.PaymentLinkResponse;
import com.kishi.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final SellerService sellerService;
    private final OrderService orderService;
    private  final SellerReportService sellerReportService;
    private final TransactionService transactionService;



    @GetMapping("/api/payment/{paymentId}")
    public ResponseEntity<ApiResponse>paymentSuccessHandler(
            @PathVariable String paymentId,
            @RequestParam String paymentLinkId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user =userService.findUserByJwtToken(jwt);

        PaymentLinkResponse paymentResponse;

        PaymentOder paymentOder =paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess =paymentService.proceedPaymentOrder(
                paymentOder,
                paymentId,
                paymentLinkId
        );

        if(paymentSuccess){
            for(Order order:paymentOder.getOrders()){
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report =sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders()+1);
                report.setTotalEarnings(report.getTotalEarnings()+order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales()+order.getOrderItems().size());
                sellerReportService.updateSellerReport(report);
            }
        }

        ApiResponse res =new ApiResponse();
        res.setMessage("payment Successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



}
