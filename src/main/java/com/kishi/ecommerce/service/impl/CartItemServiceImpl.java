package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.model.CartItem;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.repository.CartItemRepository;
import com.kishi.ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {


    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem updatecartItem(Long userId, Long id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id);

        User cartItemUser =item.getCart().getUser();

        if(cartItemUser .getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        }
        throw new Exception("you can't update this cartItem");

    }

    @Override
    public Void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);

        User cartItemUser =item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            cartItemRepository.delete(item);
            return null;
        }
        else throw new Exception("ypu can't delete this item");
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {

        return cartItemRepository.findById(id).orElseThrow(()->new Exception("cart item not found with id"+id));
    }
}
