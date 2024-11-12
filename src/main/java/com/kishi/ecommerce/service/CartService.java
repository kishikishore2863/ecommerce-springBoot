package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Cart;
import com.kishi.ecommerce.model.CartItem;
import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.model.User;

public interface CartService {

    public CartItem addCartItem(User user , Product product, String size,int quantity);

    public Cart findUserCart(User user);

}
