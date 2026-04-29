package com.vendo.indexer_service.application.product;

import com.vendo.indexer_service.port.product.ProductIndexCommandPort;
import com.vendo.indexer_service.port.product.ProductIndexUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductIndexService implements ProductIndexUseCase {

    private final ProductIndexCommandPort productIndexCommandPort;

    @Override
    public void index(Object data) {
        productIndexCommandPort.save(data);
    }

}
