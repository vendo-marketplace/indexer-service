package com.vendo.indexer_service.adapter.product.in.controller.exception;

import com.vendo.indexer_service.domain.product.exception.ProductAlreadyReindexingException;
import com.vendo.security_lib.exception.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductIndexExceptionHandler {

    @ExceptionHandler(ProductAlreadyReindexingException.class)
    ResponseEntity<ExceptionResponse> handleProductAlreadyReindexingException(ProductAlreadyReindexingException e, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .code(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

}
