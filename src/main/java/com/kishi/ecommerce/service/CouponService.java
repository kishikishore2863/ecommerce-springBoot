package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Cart;
import com.kishi.ecommerce.model.Coupon;
import com.kishi.ecommerce.model.User;

import java.util.List;

public interface CouponService {
    Cart applyCoupon(String code,double orderValue, User user) throws Exception;
    Cart removeCoupon(String code,User user) throws Exception;
    Coupon findCouponByID(Long id) throws Exception;
    Coupon createCoupon(Coupon coupon);
    List<Coupon>findAllCoupons();
    void deleteCoupon(Long id) throws Exception;

}
