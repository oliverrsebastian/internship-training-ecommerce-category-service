package com.ecommerce.category;

import com.ecommerce.category.model.Category;
import com.ecommerce.category.repository.CategoryRepository;
import com.ecommerce.category.validators.CategoryNameUniqueValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintValidatorContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryNameValidatorTest {
    private CategoryNameUniqueValidator validator;

    private CategoryRepository categoryRepository;

    private ConstraintValidatorContext context;

    @Before
    public void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        context = Mockito.mock(ConstraintValidatorContext.class);
        this.validator = new CategoryNameUniqueValidator(categoryRepository);
    }

    @Test
    public void testValidatorSuccess() {
        mockRepositoryCategoryNameStillUnique("Category 1", true);
        Assert.assertTrue(validator.isValid("Category 1", context));
    }

    @Test
    public void testValidatorFailed() {
        mockRepositoryCategoryNameStillUnique("Category 1", false);
        Assert.assertFalse(validator.isValid("Category 1", context));
    }

    private void mockRepositoryCategoryNameStillUnique(String name, boolean unique) {
        Mockito.when(categoryRepository.findByNameContainingIgnoreCase(name))
                .thenReturn(unique ? Mono.empty() : Mono.fromSupplier(() -> new Category(1L, name)));
    }
}
