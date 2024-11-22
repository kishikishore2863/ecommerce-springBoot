package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Home;
import com.kishi.ecommerce.model.HomeCategory;

import java.util.List;

public interface HomeService {
    public Home createHomePageData(List<HomeCategory> allCategories);
}
