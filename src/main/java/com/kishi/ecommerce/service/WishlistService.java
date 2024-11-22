package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.model.Wishlist;

public interface WishlistService {

    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);
    Wishlist addProductToWishlist(User user, Product product);
}
