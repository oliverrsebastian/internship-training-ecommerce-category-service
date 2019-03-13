package com.ecommerce.category;

import com.ecommerce.category.model.Category;
import com.ecommerce.category.repository.CategoryRepository;
import com.ecommerce.category.service.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CategoryServiceTest {

    private CategoryServiceImpl categoryService;

    private CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void getCategoryByIdSuccess() {
        mockRepositoryGetCategoryById(1L, true);
        Category category = categoryService.getById(1L).block();
        Assert.assertNotNull(category);
        Assert.assertEquals(java.util.Optional.of(1L).get(), category.getId());
    }

    private void mockRepositoryGetCategoryById(Long id, boolean found) {
        Mockito.when(categoryRepository.findById(id))
                .thenReturn(found ? Mono.fromSupplier(() -> new Category(1L, "Category")) : Mono.empty());
    }

    @Test
    public void getCategoryByIdNotFoundFailed() {
        mockRepositoryGetCategoryById(1L, false);
        Category category = categoryService.getById(1L).block();
        Assert.assertNull(category);
    }

    @Test
    public void getCategoryByNameSuccess() {
        mockRepositoryGetCategoryByName("Category", true);
        Category category = categoryService.getByName("Category").block();
        Assert.assertNotNull(category);
        Assert.assertEquals("Category", category.getName());
    }

    private void mockRepositoryGetCategoryByName(String name, boolean found) {
        Mockito.when(categoryRepository.findByNameContainingIgnoreCase(name))
                .thenReturn(found ? Mono.fromSupplier(() -> new Category(1l, "Category")) : Mono.empty());
    }

    @Test
    public void getCategoryByNameNotFoundFailed() {
        mockRepositoryGetCategoryByName("Category", false);
        Category category = categoryService.getByName("Category").block();
        Assert.assertNull(category);
    }

    @Test
    public void getCategoryByIdNullFailed() {
        mockRepositoryGetCategoryById(null, false);
        Category category = categoryService.getById(null).block();
        Assert.assertNull(category);
    }

    @Test
    public void getAllCategorySuccess() {
        mockRepositoryGetAllCategoryOneData(true);
        Assert.assertNotNull(categoryService.getAll().collectList().block());
        Assert.assertEquals(1, categoryService.getAll().collectList().block().size());
    }

    private void mockRepositoryGetAllCategoryOneData(boolean found) {
        Mockito.when(categoryRepository.findAll())
                .thenReturn(found ? Flux.<Category>create(sink -> {
                    sink.next(new Category(1L, "Category"));
                    sink.complete();
                }) : Flux.empty());
    }

    @Test
    public void saveCategorySuccess() {
        Category categoryWantedToBeSaved = new Category();
        categoryWantedToBeSaved.setName("Category 1");
        mockRepositorySaveCategory(categoryWantedToBeSaved, true);
        mockRepositoryGetCategoryCount(true);
        Category category = categoryService.insertCategory(categoryWantedToBeSaved).block();
        Assert.assertNotNull(category);
        Assert.assertEquals(java.util.Optional.of(1L).get(), category.getId());
        Assert.assertEquals("Category 1", category.getName());
    }

    private void mockRepositorySaveCategory(Category category, boolean success) {
        Mockito.when(categoryRepository.save(category))
                .thenReturn(success ? Mono.fromSupplier(() -> {
                    category.setId(1L);
                    return category;
                }) : Mono.empty());
    }

    private void mockRepositoryGetCategoryCount(boolean isEmpty) {
        Mockito.when(categoryRepository.count())
                .thenReturn(isEmpty ? Mono.fromSupplier(() -> 0L) : Mono.fromSupplier(() -> 1L));
    }

    @Test
    public void saveCategoryNullFailed() {
        Category categoryWantedToBeSaved = null;
        mockRepositorySaveCategory(categoryWantedToBeSaved, false);
        mockRepositoryGetCategoryCount(true);
        Category category = categoryService.insertCategory(categoryWantedToBeSaved).block();
        Assert.assertNull(category);
    }

    @Test
    public void editCategorySuccess() {
        Category categoryEdited = new Category(1L, "Category 10");
        mockRepositoryGetCategoryById(1L, true);
        mockRepositorySaveCategory(categoryEdited, true);
        Category category = categoryService.getById(1L).block();
        Assert.assertNotEquals(category, categoryEdited);
        category = categoryService.updateCategory(categoryEdited).block();
        Assert.assertEquals(category, categoryEdited);
    }

    @Test
    public void editCategoryNotFoundFailed() {
        mockRepositoryGetCategoryById(1L, false);
        Category category = categoryService.getById(1L).block();
        Assert.assertNull(category);
    }

    @Test
    public void editCategoryNullFailed() {
        mockRepositoryGetCategoryById(1L, true);
        Category category = categoryService.getById(1L).block();
        Category categoryEdited = null;
        mockRepositorySaveCategory(null, false);
        Assert.assertNotEquals(category, categoryEdited);
        category = categoryService.updateCategory(categoryEdited).block();
        Assert.assertNull(category);
    }

    public void mockRepositoryDeleteByDataFoundById(Long id) {
        Mockito.when(categoryRepository.findById(id).flatMap(data -> categoryRepository.delete(data).thenReturn(data)))
                .thenReturn(Mono.fromSupplier(() -> new Category(1L, "Category")));
    }

    public void mockRepositoryDeleteByData(Category category) {
        Mockito.when(categoryRepository.delete(Mockito.any(Category.class)))
                .thenReturn(Mono.empty());
    }

    @Test
    public void deleteCategoryByIdSuccess() {
        mockRepositoryGetCategoryById(1L, true);
        Category category = categoryService.getById(1L).block();
        mockRepositoryDeleteByData(category);
        category = categoryService.deleteById(1L).block();
        Assert.assertNotNull(category);
        Assert.assertEquals(java.util.Optional.of(1L).get(), category.getId());
    }

    @Test
    public void deleteCategoryByIdNotFoundFailed() {
        mockRepositoryGetCategoryById(1L, false);
        Category category = categoryService.deleteById(1L).block();
        Assert.assertNull(category);
    }

    @Test
    public void deleteCategoryByIdNullFailed() {
        mockRepositoryGetCategoryById(null, false);
        Category category = categoryService.deleteById(null).block();
        Assert.assertNull(category);
    }
}
