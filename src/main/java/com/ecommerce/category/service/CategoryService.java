package com.ecommerce.category.service;

import com.ecommerce.category.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Mono<Category> getById(Long id);

    Mono<Category> getByName(String name);

    Flux<Category> getAll();

    Mono<Category> insertCategory(Category category);

    Mono<Category> updateCategory(Category category);

    Mono<Category> deleteById(Long id);
}
