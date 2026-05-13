package com.vendo.indexer_service.domain.product.exception;

public class ProductAlreadyReindexingException extends RuntimeException {
    public ProductAlreadyReindexingException(String message) {
        super(message);
    }
}
