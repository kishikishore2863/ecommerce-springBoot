package com.kishi.ecommerce.controller;

import com.kishi.ecommerce.domain.USER_ROLE;
import com.kishi.ecommerce.model.VerificationCode;
import com.kishi.ecommerce.request.LoginOtpRequest;
import com.kishi.ecommerce.response.ApiResponse;
import com.kishi.ecommerce.response.AuthResponse;
import com.kishi.ecommerce.request.LoginRequest;
import com.kishi.ecommerce.response.SignupRequest;
import com.kishi.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {
        String jwt = authService.createUser(req);
        AuthResponse res =new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {
       authService.sentLoginOtp(req.getEmail(),req.getRole());
        ApiResponse res =new ApiResponse();
        res.setMessage("otp sent  success");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse>loginHandler(@RequestBody LoginRequest req){
        AuthResponse authResponse = authService.siging(req);
        return  ResponseEntity.ok(authResponse);
    }
}
