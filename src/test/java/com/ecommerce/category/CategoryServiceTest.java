package com.ecommerce.category;

import com.ecommerce.category.model.Category;
import com.ecommerce.category.service.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryServiceTest {

    private CategoryServiceImpl categoryService;

    @Before
    public void setUp() throws Exception {
        categoryService = new CategoryServiceImpl();
    }

    @Test
    public void getCategoryByIdSuccess() {
        Category category = categoryService.getById("CAT1");
        Assert.assertNotNull(category);
        Assert.assertEquals("CAT1", category.getId());
    }

    @Test
    public void getCategoryByIdNotFoundFailed() {
        Category category = categoryService.getById("CAT10");
        Assert.assertNull(category);
    }

    @Test
    public void getCategoryByIdNullFailed() {
        Category category = categoryService.getById(null);
        Assert.assertNull(category);
    }

    @Test
    public void getCategoryByIdNotStartedWithPrefixFailed() {
        Category category = categoryService.getById("10");
        Assert.assertNull(category);
    }

    @Test
    public void getAllCategorysSuccess() {
        Assert.assertNotNull(categoryService.getAll());
        Assert.assertEquals(5, categoryService.getAll().size());
    }

    @Test
    public void saveCategorySuccess() {
        Category categoryWantedToBeSaved = new Category("CAT1", "Category 1");
        Category category = categoryService.insertCategory(categoryWantedToBeSaved);
        Assert.assertNotNull(category);
        Assert.assertEquals("CAT1", category.getId());
    }

    @Test
    public void saveCategoryNullFailed() {
        Category categoryWantedToBeSaved = null;
        Category category = categoryService.insertCategory(categoryWantedToBeSaved);
        Assert.assertNull(category);
    }

    @Test
    public void editCategorySuccess() {
        Category category = categoryService.getById("CAT1");
        Category categoryEdited = new Category("CAT1", "Category 10");
        Assert.assertNotEquals(category, categoryEdited);
        category = categoryService.updateCategory(categoryEdited);
        Assert.assertEquals(category, categoryEdited);
    }

    @Test
    public void editCategoryNotFoundFailed() {
        Category category = categoryService.getById("CAT10");
        Assert.assertNull(category);
    }

    @Test
    public void editCategoryNullFailed() {
        Category category = categoryService.getById("CAT1");
        Category categoryEdited = null;
        Assert.assertNotEquals(category, categoryEdited);
        category = categoryService.updateCategory(categoryEdited);
        Assert.assertNull(category);
    }

    @Test
    public void deleteCategoryByIdSuccess() {
        Category category = categoryService.deleteById("CAT1");
        Assert.assertNotNull(category);
        Assert.assertEquals("CAT1", category.getId());
    }

    @Test
    public void deleteCategoryByIdNotFoundFailed() {
        Category category = categoryService.deleteById("CAT10");
        Assert.assertNull(category);
    }

    @Test
    public void deleteCategoryByIdNullFailed() {
        Category category = categoryService.deleteById(null);
        Assert.assertNull(category);
    }

    @Test
    public void deleteCategoryByIdNotStartedWithPrefixFailed() {
        Category category = categoryService.deleteById("10");
        Assert.assertNull(category);
    }
}
