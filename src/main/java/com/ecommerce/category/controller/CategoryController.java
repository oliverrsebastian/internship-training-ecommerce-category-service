package com.ecommerce.category.controller;

import com.ecommerce.category.model.ApiKey;
import com.ecommerce.category.model.Category;
import com.ecommerce.category.service.CategoryService;
import com.ecommerce.category.validators.ValidationHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    private ValidationHelper validationHelper;

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              ValidationHelper validationHelper,
                              KafkaTemplate<String, String> kafkaTemplate,
                              ObjectMapper objectMapper) {
        this.categoryService = categoryService;
        this.validationHelper = validationHelper;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Category> getAllCategories() {
        return categoryService.getAll()
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(path = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id).subscribeOn(Schedulers.elastic());
    }

    @GetMapping(path = "/categories/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Category> getCategoryByName(@PathVariable String name) {
        return categoryService.getByName(name).subscribeOn(Schedulers.elastic());
    }

    @PostMapping(path = "/categories/_insert", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Category> insertCategory(@RequestBody Category category) {
        return validationHelper.validateObject(category).flatMap(data -> categoryService.insertCategory(data))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(path = "/categories/_edit", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Category> updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category).subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(path = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Category> deleteCategoryById(@PathVariable Long id, ApiKey apiKey) {
        return categoryService.deleteById(id).subscribeOn(Schedulers.elastic());
    }

    @GetMapping(path = "/categories/refresh/publish", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Category> refreshCategoryOnKafka() {
        return categoryService.getAll()
                .doOnNext(value -> {
                    String json = null;
                    try {
                        json = objectMapper.writeValueAsString(value);
                        kafkaTemplate.send("categories", json);
                    } catch (JsonProcessingException ignore) {
                    }
                }).subscribeOn(Schedulers.elastic());
    }
}
