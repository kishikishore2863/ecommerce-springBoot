package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.CartItem;

public interface CartItemService {

    CartItem updatecartItem(Long userId,Long id , CartItem cartItem) throws Exception;

    Void removeCartItem(Long userId,Long cartItemId) throws Exception;

    CartItem findCartItemById(Long id) throws Exception;
}
