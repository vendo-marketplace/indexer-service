package com.vendo.indexer_service.adapter.product.in;

import com.vendo.indexer_service.port.product.index.ProductReindexUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class ProductReindexController {

    private final ProductReindexUseCase useCase;

    @PostMapping("/reindex")
    void reindexAll() {
        useCase.reindex();
    }

}
