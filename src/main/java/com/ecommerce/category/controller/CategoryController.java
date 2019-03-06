package com.ecommerce.category.controller;

import com.ecommerce.category.model.Category;
import com.ecommerce.category.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    @GetMapping(path = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Category getCategoryById(@PathVariable String id) {
        return categoryService.getById(id);
    }

    @PostMapping(path = "/categories/_insert", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Category insertCategory(@RequestBody Category category) {
        return categoryService.insertCategory(category);
    }

    @PutMapping(path = "/categories/_update", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping(path = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Category deleteCategoryById(@PathVariable String id) {
        return categoryService.deleteById(id);
    }

}
