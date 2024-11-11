package com.kishi.ecommerce.model;

import com.kishi.ecommerce.domain.PaymentMethod;
import com.kishi.ecommerce.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    private PaymentMethod paymentMethod;

    private  String paymentLinkId;

    @ManyToOne
    private User user;

    @OneToMany
    private Set<Order>orders = new HashSet<>();


}
