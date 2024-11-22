package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.HomeCategory;

import java.util.List;

public interface HomeCategoryService {
    HomeCategory createHomeCategory(HomeCategory homeCategory);
    List<HomeCategory>createCategories(List<HomeCategory> homeCategories);
    HomeCategory updateHomeCategory(HomeCategory homeCategory,Long id) throws Exception;
    List<HomeCategory>getAllHomeCategories();
}
