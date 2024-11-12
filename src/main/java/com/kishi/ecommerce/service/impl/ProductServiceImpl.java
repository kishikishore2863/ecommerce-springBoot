package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.exceptions.ProductException;
import com.kishi.ecommerce.model.Category;
import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.model.Seller;
import com.kishi.ecommerce.repository.CategoryRepository;
import com.kishi.ecommerce.repository.ProductRepository;
import com.kishi.ecommerce.request.CreateProductRequest;
import com.kishi.ecommerce.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


//    @Override
//    public Product createProduct(CreateProductRequest req, Seller seller) {
//        Category category1 = categoryRepository.findByCategoryId(req.getCategory());
//        if(category1 == null){
//            Category category =new Category();
//            category.setCategoryId(req.getCategory());
//            category.setLevel(1);
//            category1=categoryRepository.save(category);
//        }
//        Category category2 = categoryRepository.findByCategoryId(req.getCategory2());
//        if(category2 == null){
//            Category category =new Category();
//            category.setCategoryId(req.getCategory());
//            category.setLevel(2);
//            category.setParentCategory(category1);
//            category2=categoryRepository.save(category);
//        }
//        Category category3 = categoryRepository.findByCategoryId(req.getCategory3());
//        if(category3 == null){
//            Category category =new Category();
//            category.setCategoryId(req.getCategory());
//            category.setLevel(2);
//            category.setParentCategory(category2);
//            category3=categoryRepository.save(category);
//        }
//
//        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(),req.getSellingPrice());
//
//        Product newProduct =new Product();
//        newProduct.setSeller(seller);
//        newProduct.setCategory(category3);
//        newProduct.setDescription(req.getDescription());
//        newProduct.setCreatedAt(LocalDateTime.now());
//        newProduct.setTitle(req.getTitle());
//        newProduct.setColor(req.getColor());
//        newProduct.setSellingPrice(req.getSellingPrice());
//        newProduct.setMrpPrice(req.getMrpPrice());
//        newProduct.setImages(req.getImages());
//        newProduct.setSizes(req.getSizes());
//        newProduct.setDiscountPercent(discountPercentage);
//
//        return productRepository.save(newProduct);
//
//    }
//


    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {
        // Check and create the first category if not already present
        Category category1 = categoryRepository.findByCategoryId(req.getCategory());
        if (category1 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory()); // Set category ID from the request
            category.setLevel(1);
            category1 = categoryRepository.save(category); // Save if doesn't exist
        }

        // Check and create the second category if not already present
        Category category2 = categoryRepository.findByCategoryId(req.getCategory2());
        if (category2 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory2()); // Use category2 ID from the request
            category.setLevel(2);
            category.setParentCategory(category1); // Set parent category
            category2 = categoryRepository.save(category); // Save if doesn't exist
        }

        // Check and create the third category if not already present
        Category category3 = categoryRepository.findByCategoryId(req.getCategory3());
        if (category3 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory3()); // Use category3 ID from the request
            category.setLevel(3);  // Set level 3
            category.setParentCategory(category2); // Set parent category to category2
            category3 = categoryRepository.save(category); // Save if doesn't exist
        }

        // Calculate discount percentage
        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());

        // Create and set up the new product
        Product newProduct = new Product();
        newProduct.setSeller(seller);
        newProduct.setCategory(category3); // Set category as category3
        newProduct.setDescription(req.getDescription());
        newProduct.setCreatedAt(LocalDateTime.now());
        newProduct.setTitle(req.getTitle());
        newProduct.setColor(req.getColor());
        newProduct.setSellingPrice(req.getSellingPrice());
        newProduct.setMrpPrice(req.getMrpPrice());
        newProduct.setImages(req.getImages());
        newProduct.setSizes(req.getSizes());
        newProduct.setDiscountPercent(discountPercentage);

        // Save the product
        return productRepository.save(newProduct);
    }




    private int  calculateDiscountPercentage(int mrpPrice,int sellingPrice){
        if(mrpPrice<=0){
            throw new IllegalArgumentException("actual price must be greater than 0");

        }
        double discount = mrpPrice-sellingPrice;
        double discountPercentage=(discount/mrpPrice)*100;
        return (int)discountPercentage;
    }


    @Override
    public void deleteProduct(Long productId) throws ProductException {
     Product product = findProductById(productId);
     productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        findProductById(productId);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        return productRepository.findById(productId).orElseThrow(()->new ProductException("product not found with id"+productId));
    }

    @Override
    public List<Product> searchProducts(String query) {

        return productRepository.searchProduct(query);
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
        Specification<Product>spec = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>();

            if (category!=null){
                Join<Product,Category>categoryJoin= root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("category"),category));
            }
            if(colors!=null && !colors.isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("color"),colors));
            }
            if(sizes != null && !sizes.isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("size"),sizes));
            }
            if(minPrice !=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"),minPrice));
            }
            if(maxPrice !=null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"),maxPrice));
            }
            if(minDiscount!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPrecent"),minDiscount));
            }
            if(stock!=null){
                predicates.add(criteriaBuilder.equal(root.get("stock"),stock));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });

        Pageable pageable;
        if(sort!=null && !sort.isEmpty()){
            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").ascending());
                case "price_high" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").descending());
                default -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.unsorted());
            };
        }else {
            pageable=PageRequest.of(pageNumber!=null ? pageNumber:0,10,Sort.unsorted());
        }

         return productRepository.findAll(spec,pageable);

    }

    @Override
    public List<Product> getProductBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }
}