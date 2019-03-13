package com.ecommerce.category;

import com.ecommerce.category.model.ApiKeyMethodArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@SpringBootApplication
public class CategoryApplication implements WebFluxConfigurer {

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new ApiKeyMethodArgumentResolver());
    }

    public static void main(String[] args) {
        SpringApplication.run(CategoryApplication.class, args);
    }

}
