package com.vendo.indexer_service.application.product;

import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.port.product.ProductCommandPort;
import com.vendo.indexer_service.port.product.index.ProductIndexUseCase;
import com.vendo.utils_lib.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductIndexService implements ProductIndexUseCase {

    private final ProductCommandPort productCommandPort;

    @Override
    public void save(Product product) {
        if (Objects.isNull(product)) {
            log.error("Product is not present.");
            return;
        }

        productCommandPort.save(product);
    }

    @Override
    public void update(String id, Product product) {
        if (StringUtils.isEmpty(id) || Objects.isNull(product)) {
            log.error("Invalid update body. Id: {}, Product: {}.", id, product);
            return;
        }

        productCommandPort.update(id, product);
    }

}
