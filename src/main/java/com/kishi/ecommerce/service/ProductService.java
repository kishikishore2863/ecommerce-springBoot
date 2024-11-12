package com.kishi.ecommerce.service;

import com.kishi.ecommerce.exceptions.ProductException;
import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.model.Seller;
import com.kishi.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;


import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req, Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId,Product product) throws ProductException;
    Product findProductById(Long productId) throws ProductException;
    List<Product>searchProducts(String query);
    public Page<Product> getAllProducts(

            String category,
            String brand,
            String colors,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );

    List<Product>getProductBySellerId(Long sellerId);


}
