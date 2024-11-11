package com.kishi.ecommerce.controller;

import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.response.AuthResponse;
import com.kishi.ecommerce.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/users/profile")
    public ResponseEntity<User>createUserHandler(@RequestHeader("Authorization") String jwt)throws Exception{
        User user =userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(user);
    }



}
