package com.ecommerce.category.model;

public class ApiKeyException extends RuntimeException {
    public ApiKeyException() {
        super("Unauthorized");
    }
}
