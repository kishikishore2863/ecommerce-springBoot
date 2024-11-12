package com.kishi.ecommerce.repository;

import com.kishi.ecommerce.model.Cart;
import com.kishi.ecommerce.model.CartItem;
import com.kishi.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

   CartItem findByCartAndProductAndSize(Cart cart , Product product,String size);
}
