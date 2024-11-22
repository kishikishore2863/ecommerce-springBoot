package com.kishi.ecommerce.repository;

import com.kishi.ecommerce.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal,Long> {
}
