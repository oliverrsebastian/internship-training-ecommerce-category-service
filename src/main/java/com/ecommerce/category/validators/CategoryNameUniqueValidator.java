package com.ecommerce.category.validators;

import com.ecommerce.category.annotations.NameUnique;
import com.ecommerce.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CategoryNameUniqueValidator implements ConstraintValidator<NameUnique, String> {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryNameUniqueValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return categoryRepository.findByNameContainingIgnoreCase(s).block() == null;
    }
}
