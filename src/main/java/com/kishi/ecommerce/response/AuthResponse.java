package com.kishi.ecommerce.response;

import com.kishi.ecommerce.domain.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE role;


}
