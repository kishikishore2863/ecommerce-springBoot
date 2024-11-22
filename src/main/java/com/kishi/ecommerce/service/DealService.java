package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Deal;

import java.util.List;

public interface DealService {
    List<Deal> getDeals();
    Deal createDeal(Deal deal);
    Deal updatedeal(Deal deal);
    void delete(Long id) throws Exception ;

}
