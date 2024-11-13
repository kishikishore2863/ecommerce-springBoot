package com.kishi.ecommerce.service;

import com.kishi.ecommerce.domain.OrderStatus;
import com.kishi.ecommerce.model.Address;
import com.kishi.ecommerce.model.Cart;
import com.kishi.ecommerce.model.Order;
import com.kishi.ecommerce.model.User;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderByIs(Long id);

    List<Order> usersOrderHistory(Long userId);

    List<Order>sellerOrder(Long sellerId);

    Order updateOrderStatus(Long OrderId, OrderStatus status);

    Order cancelOrder(Long orderId,User user);

}
