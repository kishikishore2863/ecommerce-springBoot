package com.kishi.ecommerce.controller;

import com.kishi.ecommerce.exceptions.ProductException;
import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.model.Wishlist;
import com.kishi.ecommerce.service.ProductService;
import com.kishi.ecommerce.service.UserService;
import com.kishi.ecommerce.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Wishlist>getWishByUserId(@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Wishlist wishlist=wishlistService.getWishlistByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);
        Wishlist updatedWishlist = wishlistService.addProductToWishlist(user,product);
        return ResponseEntity.ok(updatedWishlist);
    }


}
