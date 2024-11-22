package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.model.Order;
import com.kishi.ecommerce.model.Seller;
import com.kishi.ecommerce.model.Transaction;
import com.kishi.ecommerce.repository.SellerRepository;
import com.kishi.ecommerce.repository.TransactionRespository;
import com.kishi.ecommerce.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private  final TransactionRespository transactionRespository;
    private final SellerRepository sellerRepository;
    @Override
    public Transaction createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);

        return transactionRespository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(Seller seller) {
        return transactionRespository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRespository.findAll();
    }
}
