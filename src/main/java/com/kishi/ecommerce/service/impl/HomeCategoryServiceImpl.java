package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.model.HomeCategory;
import com.kishi.ecommerce.repository.HomeCategoryRepository;
import com.kishi.ecommerce.service.HomeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategoryService {

    private final HomeCategoryRepository homeCategoryRepository;

    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {
        if(homeCategoryRepository.findAll().isEmpty()){
            return homeCategoryRepository.saveAll(homeCategories);
        }
        return homeCategoryRepository.findAll();
    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory category, Long id) throws Exception {
        HomeCategory existingcategory = homeCategoryRepository.findById(id)
                .orElseThrow(()->new Exception("Category not found"));
        if(category.getImage()!=null){
            existingcategory.setImage(category.getImage());
        }
        if(category.getCategoryId()!=null){
            existingcategory.setCategoryId(category.getCategoryId());
        }
        return homeCategoryRepository.save(existingcategory);
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();

    }
}
