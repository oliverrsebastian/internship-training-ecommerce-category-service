package com.ecommerce.category.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidationHelper {
    private Validator validator;

    @Autowired
    public ValidationHelper(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> validateObject(T request) {
        return Mono.fromSupplier(() -> {
            Set<ConstraintViolation<T>> violations = validator.validate(request);
            if (violations.isEmpty())
                return request;
            else
                violations.forEach(violation -> {
                    violation.getMessage(); //get error message for every field
                });
            throw new IllegalArgumentException("Validation Error");
        });
    }
}
