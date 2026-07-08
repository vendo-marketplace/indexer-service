package com.vendo.indexer_service.infrastructure.aop;

import com.vendo.indexer_service.port.product.index.ProductIndexPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ProductIndexAspect {

    private final ProductIndexPort productIndexPort;

    @Before("execution(* com.vendo.indexer_service.application.product.*(..))")
    public void ensureIndexCreation() {
        if (!productIndexPort.exists()) {
            log.info("Product index doesn't exist. Creating.");
            productIndexPort.create();
        }
    }
}
