package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.model.Cart;
import com.kishi.ecommerce.model.CartItem;
import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.repository.CartItemRepository;
import com.kishi.ecommerce.repository.CartRepository;
import com.kishi.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) {

        Cart cart = findUserCart(user);

        CartItem isPresent=cartItemRepository.findByCartAndProductAndSize(cart,product,size);

        if(isPresent == null ){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPrice =quantity*product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity*product.getMrpPrice());

            cart.getCartItem().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart= cartRepository.findByUserId(user.getId());
        int totalPrice=0;
        int totalDiscountPrice=0;
        int totalItem=0;

        for(CartItem cartItem: cart.getCartItem()){
            totalPrice+=cartItem.getMrpPrice();
            totalDiscountPrice+=cartItem.getSellingPrice();
            totalItem+=cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalSellingPrice(totalDiscountPrice);
        cart.setDiscount(calculateDiscountPercentage(totalPrice,totalDiscountPrice));
        cart.setTotalItem(totalItem);

        return cart;
    }
    private int  calculateDiscountPercentage(int mrpPrice,int sellingPrice){
        if(mrpPrice<=0){
            return 0;

        }
        double discount = mrpPrice-sellingPrice;
        double discountPercentage=(discount/mrpPrice)*100;
        return (int)discountPercentage;
    }
}
