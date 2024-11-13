package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.domain.OrderStatus;
import com.kishi.ecommerce.model.Address;
import com.kishi.ecommerce.model.Cart;
import com.kishi.ecommerce.model.Order;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.repository.AddressRepository;
import com.kishi.ecommerce.repository.OrderRepository;
import com.kishi.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
        if(!user.getAddresses().contains(shippingAddress)){
            user.getAddresses().add(shippingAddress);
        }
        Address address = addressRepository.save(shippingAddress);


        return null;
    }

    @Override
    public Order findOrderByIs(Long id) {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return null;
    }

    @Override
    public List<Order> sellerOrder(Long sellerId) {
        return null;
    }

    @Override
    public Order updateOrderStatus(Long OrderId, OrderStatus status) {
        return null;
    }

    @Override
    public Order cancelOrder(Long orderId, User user) {
        return null;
    }
}
