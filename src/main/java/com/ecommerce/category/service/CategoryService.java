package com.ecommerce.category.service;

import com.ecommerce.category.model.Category;

import java.util.List;

public interface CategoryService {
    Category getById(String id);

    List<Category> getAll();

    Category insertCategory(Category category);

    Category updateCategory(Category category);

    Category deleteById(String id);
}
