package com.vendo.indexer_service.infrastructure.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.vendo.indexer_service.adapter.product.out")
public class OpenFeignConfig {
}
