package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.config.JwtProvider;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.repository.UserRepository;
import com.kishi.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private  final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user =  this.findUserByEmail(email);
        if(user ==null){
            throw new Exception("user not found with email "+email);
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user ==null){
            throw new Exception("user not found with email "+email);
        }

        return user;
    }
}
