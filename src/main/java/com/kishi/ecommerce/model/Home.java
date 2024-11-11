package com.kishi.ecommerce.model;

import lombok.Data;

import java.util.List;

@Data
public class Home {
    private List<HomeCategory> grid;
    private List<HomeCategory> showByCategories;

    private List<HomeCategory> electriccategories;

    private List<HomeCategory>dealCategories;

    private List<Deal> deals;
}
