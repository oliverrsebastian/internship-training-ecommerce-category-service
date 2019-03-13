package com.ecommerce.category.repository;

import com.ecommerce.category.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<Category, Long> {
    Mono<Category> findByNameContainingIgnoreCase(String name);
}
