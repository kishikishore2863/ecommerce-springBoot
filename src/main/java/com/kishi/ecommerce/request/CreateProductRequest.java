package com.kishi.ecommerce.request;

import com.kishi.ecommerce.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {

    private String title;
    private String description;
    private int mrpPrice;
    private int sellingPrice;
    private int discountPercent;
    private int quantity;
    private String color;
    private List<String> images;
    private String category;
    private String category2;
    private String category3;

    private String sizes;



}
