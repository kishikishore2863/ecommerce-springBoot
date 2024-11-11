package com.kishi.ecommerce.controller;

import com.kishi.ecommerce.model.VerificationCode;
import com.kishi.ecommerce.repository.VerificationCodeRepository;
import com.kishi.ecommerce.request.LoginOtpRequest;
import com.kishi.ecommerce.response.ApiResponse;
import com.kishi.ecommerce.service.AuthService;
import com.kishi.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private AuthService authService;


    @PostMapping("/sent/login-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {
        authService.sentLoginOtp(req.getEmail(),req.getRole());
        ApiResponse res =new ApiResponse();
        res.setMessage("otp sent  success");

        return ResponseEntity.ok(res);
    }




}
