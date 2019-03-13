package com.ecommerce.category.service;

import com.ecommerce.category.model.Category;
import com.ecommerce.category.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Mono<Category> getById(Long id) {
        if (id != null) {
            return categoryRepository.findById(id);
        }
        return Mono.empty();
    }

    @Override
    public Mono<Category> getByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Flux<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> insertCategory(Category category) {
        if (category != null) {
            return categoryRepository.count()
                    .map(data -> data + 1)
                    .map(data -> setCategoryId(category, data))
                    .then(categoryRepository.save(category))
                    .thenReturn(category);
        }
        return Mono.empty();
    }

    private Long setCategoryId(Category category, Long data) {
        category.setId(data);
        logger.info("ID : " + category.getId());
        return category.getId();
    }

    @Override
    public Mono<Category> updateCategory(Category category) {
        if (category != null) {
            return categoryRepository.save(category);
        }
        return Mono.empty();
    }

    @Override
    public Mono<Category> deleteById(Long id) {
        return categoryRepository.findById(id)
                .flatMap(data -> categoryRepository.delete(data)
                        .thenReturn(data));
    }
}
