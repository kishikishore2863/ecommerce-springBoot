package com.kishi.ecommerce.controller;

import com.kishi.ecommerce.exceptions.ProductException;
import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return  new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam(required = false)String query){
        List<Product> products =productService.searchProducts(query);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<Product>> getAllProducts(
            @RequestParam(required = false)String category,
            @RequestParam(required = false)String brand,
            @RequestParam(required = false)String color,
            @RequestParam(required = false)String size,
            @RequestParam(required = false)Integer minPrice,
            @RequestParam(required = false)Integer maxPrice,
            @RequestParam(required = false)Integer minDiscount,
            @RequestParam(required = false)String sort,
            @RequestParam(required = false)String stock,
            @RequestParam(defaultValue ="0") Integer pageNumber){
       Page<Product> res = productService.getAllProducts(category,brand,
                color,size,minPrice,
                maxPrice,minDiscount,sort,
                stock,pageNumber);
        return ResponseEntity.ok(res);
    }
}
