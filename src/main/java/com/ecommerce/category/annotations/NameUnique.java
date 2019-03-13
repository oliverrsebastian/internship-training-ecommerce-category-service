package com.ecommerce.category.annotations;

import com.ecommerce.category.validators.CategoryNameUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CategoryNameUniqueValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface NameUnique {

    String message() default "Unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
