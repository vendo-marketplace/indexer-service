package com.vendo.indexer_service.application.product;

import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.domain.product.exception.ProductAlreadyReindexingException;
import com.vendo.indexer_service.port.product.ProductQueryPort;
import com.vendo.indexer_service.port.product.index.ProductReindexUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ProductReindexService implements ProductReindexUseCase {

    private final ReentrantLock indexLock = new ReentrantLock();

//    private final ProductQueryPort productQueryPort;

    private static final int BATCH_SIZE = 1000;

    @Async
    @Override
    public void reindex() {
        try {
            boolean locked = indexLock.tryLock();
            if (locked) throw new ProductAlreadyReindexingException("Product reindexing already in progress.");

//            List<Product> products = productQueryPort.getAll(null, BATCH_SIZE);
//            while (products.size() < BATCH_SIZE) {
//
//            }

        } finally {
            indexLock.unlock();
        }
    }
}
