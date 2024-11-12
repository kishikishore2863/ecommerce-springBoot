package com.kishi.ecommerce.service;

import com.kishi.ecommerce.domain.USER_ROLE;
import com.kishi.ecommerce.response.AuthResponse;
import com.kishi.ecommerce.request.LoginRequest;
import com.kishi.ecommerce.response.SignupRequest;

public interface AuthService {


    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;

    AuthResponse siging(LoginRequest req ) throws Exception;


}
