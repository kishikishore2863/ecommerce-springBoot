package com.kishi.ecommerce.service;

import com.kishi.ecommerce.domain.OrderStatus;
import com.kishi.ecommerce.model.*;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderById(Long id) throws Exception;

    List<Order> usersOrderHistory(Long userId);

    List<Order>sellerOrder(Long sellerId);

    Order updateOrderStatus(Long OrderId, OrderStatus status) throws Exception;

    Order cancelOrder(Long orderId,User user) throws Exception;

    OrderItem getOrderItemById(Long id) throws Exception;

}
