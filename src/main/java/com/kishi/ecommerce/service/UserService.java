package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
     User findUserByJwtToken(String jwt) throws Exception;
     User findUserByEmail(String email) throws Exception;


}
