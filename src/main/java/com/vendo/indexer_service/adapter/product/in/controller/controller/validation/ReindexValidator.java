package com.vendo.indexer_service.adapter.product.in.controller.controller.validation;


import com.vendo.indexer_service.domain.product.exception.ProductAlreadyReindexingException;
import com.vendo.indexer_service.port.product.index.ProductReindexPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReindexValidator {

    private final ProductReindexPort productReindexPort;

    public void validateInProgress() {
        if (productReindexPort.isProcessing()) {
            throw new ProductAlreadyReindexingException("Reindexing already in progress." );
        }
    }
}
