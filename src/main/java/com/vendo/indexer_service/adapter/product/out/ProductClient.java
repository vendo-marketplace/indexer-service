package com.vendo.indexer_service.adapter.product.out;

import com.vendo.indexer_service.adapter.product.out.config.ProductFeignConfig;
import com.vendo.indexer_service.domain.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(
        name = "product-service",
        path = "/internal/products",
        configuration = ProductFeignConfig.class)
public interface ProductClient {

    @GetMapping
    List<Product> getAll(@RequestParam("cursor") String cursor, @RequestParam("limit") int limit);

}
