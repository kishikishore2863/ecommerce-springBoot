package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Order;
import com.kishi.ecommerce.model.Seller;
import com.kishi.ecommerce.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySellerId(Seller seller);
    List<Transaction> getAllTransaction();
}
