package com.kishi.ecommerce.repository;

import com.kishi.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {
//    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    List<Product > findBySellerId(Long id);

//    @Query("SELECT p FROM Product P where (:query is null or lower(p.title)" +
//            "LIKE lower(concat('%', :query, '%')))" +
//            "or (:query is null or lower(p.category.name)))"+
//            "like lower(concat('%', :query, '%')))")
//    List<Product>searchProduct(@Param("query")String query);

    @Query("SELECT p FROM Product p WHERE " +
            "(:query IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Product> searchProduct(@Param("query") String query);


}
