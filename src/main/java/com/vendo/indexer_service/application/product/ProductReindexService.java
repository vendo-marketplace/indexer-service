package com.vendo.indexer_service.application.product;

import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.domain.product.exception.ProductAlreadyReindexingException;
import com.vendo.indexer_service.port.product.ProductQueryPort;
import com.vendo.indexer_service.port.product.index.ProductReindexPort;
import com.vendo.indexer_service.port.product.index.ProductReindexUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductReindexService implements ProductReindexUseCase {

    private final ProductQueryPort productQueryPort;
    private final ProductReindexPort productReindexPort;

    private final int REINDEX_BATCH_SIZE;

    @Async
    @Override
    public void reindex() {
        log.info("Started reindexing products." );

        List<Product> products = productQueryPort.getAll(null, REINDEX_BATCH_SIZE);
        while (!products.isEmpty()) {
            productReindexPort.reindex(products);
            products = productQueryPort.getAll(Product.getLast(products).id(), REINDEX_BATCH_SIZE);
        }

        log.info("Successfully finished reindexing." );
    }
}
