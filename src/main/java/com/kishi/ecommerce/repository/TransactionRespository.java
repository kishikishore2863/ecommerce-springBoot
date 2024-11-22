package com.kishi.ecommerce.repository;

import com.kishi.ecommerce.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRespository extends JpaRepository<Transaction,Long> {

    List<Transaction>findBySellerId(Long sellerId);

}
