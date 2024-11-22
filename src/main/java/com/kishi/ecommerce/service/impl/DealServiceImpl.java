package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.model.Deal;
import com.kishi.ecommerce.model.HomeCategory;
import com.kishi.ecommerce.repository.DealRepository;
import com.kishi.ecommerce.repository.HomeCategoryRepository;
import com.kishi.ecommerce.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;
    @Override
    public List<Deal> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) {
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);
        Deal newDeal = dealRepository.save(deal);
        newDeal.setCategory(category);
        newDeal.setDiscount(deal.getDiscount());
        return dealRepository.save(newDeal);
    }

    @Override
    public Deal updatedeal(Deal deal) {

        return null;
    }

    @Override
    public void delete(Long id) throws Exception {
        Deal deal  =dealRepository.findById(id).orElseThrow(()->new Exception("deal not found"));
            dealRepository.delete(deal);
    }
}
