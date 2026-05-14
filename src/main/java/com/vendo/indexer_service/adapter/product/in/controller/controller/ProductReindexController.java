package com.vendo.indexer_service.adapter.product.in.controller.controller;

import com.vendo.indexer_service.port.product.index.ProductReindexUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@PreAuthorize("hasAuthority('ADMIN')")
public class ProductReindexController {

    private final ProductReindexUseCase productReindexUseCase;

    @PostMapping("/reindex")
    void reindex() {
        productReindexUseCase.reindex();
    }

}
