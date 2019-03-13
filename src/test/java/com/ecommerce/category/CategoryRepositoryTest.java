package com.ecommerce.category;

import com.ecommerce.category.model.Category;
import com.ecommerce.category.repository.CategoryRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Long id;

    @Before
    public void setUp() throws Exception {
        categoryRepository.deleteAll().block();
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        category = categoryRepository.save(category).block();
        id = category.getId();
    }

    @Test
    public void testRepositoryInsertSuccess() {
        categoryRepository.count()
                .map(data -> {
                    Category category = categoryRepository.save(new Category(data + 1, "Category 2")).block();
                    Assert.assertNotNull(category);
                    Assert.assertNotNull(category.getId());
                    return data;
                });
    }

    @Test
    public void testRepositoryGetByIdSuccess() {
        Category category = categoryRepository.findById(id).block();
        Assert.assertNotNull(category);
    }

    @Test
    public void testRepositoryGetAllSuccess() {
        List<Category> categories = categoryRepository.findAll().collectList().block();
        Assert.assertEquals(1, categories.size());
    }

    @Test
    public void testRepositoryEditSuccess() {
        Category category = new Category();
        category.setId(id);
        category.setName("Category 30");

        Category foundById = categoryRepository.findById(id).block();
        Assert.assertNotNull(foundById);
        Assert.assertNotEquals(category, foundById);
        category = categoryRepository.save(category).block();
        foundById = categoryRepository.findById(id).block();
        Assert.assertNotNull(foundById);
        Assert.assertEquals(category, foundById);
    }

    @Test
    public void testRepositoryDeleteByIdSuccess() {
        categoryRepository.deleteById(1L)
                .map(data -> {
                    Assert.assertNull(categoryRepository.findById(1L).block());
                    return data;
                });
    }

    @Test
    public void testRepositoryFindByIdThenDeleteItSuccess() {
        Category category = categoryRepository.findById(1L)
                .flatMap(data -> categoryRepository.delete(data)
                        .thenReturn(data)).block();
        Assert.assertEquals(Optional.of(1L).get(), category.getId());
        Assert.assertNull(categoryRepository.findById(1L).block());
    }

    @After
    public void tearDown() throws Exception {
        categoryRepository.deleteAll().block();
    }

}
