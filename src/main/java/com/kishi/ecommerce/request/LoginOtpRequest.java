package com.kishi.ecommerce.request;

import com.kishi.ecommerce.domain.USER_ROLE;
import lombok.Data;

@Data
public class LoginOtpRequest {
   private String email;
   private String otp;
   private USER_ROLE role;
}
