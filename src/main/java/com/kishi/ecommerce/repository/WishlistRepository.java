package com.kishi.ecommerce.repository;

import com.kishi.ecommerce.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    Wishlist findByUserId(Long UserId);
}
