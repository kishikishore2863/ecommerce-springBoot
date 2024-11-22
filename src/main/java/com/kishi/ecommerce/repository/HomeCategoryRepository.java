package com.kishi.ecommerce.repository;

import com.kishi.ecommerce.model.HomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeCategoryRepository extends JpaRepository<HomeCategory,Long> {
}
