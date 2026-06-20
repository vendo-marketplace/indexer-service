package com.vendo.indexer_service.adapter.product.in.controller.interceptor;

import com.vendo.core_lib.type.ServiceName;
import com.vendo.indexer_service.adapter.security.out.jwt.InternalGenerationPort;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.vendo.security_lib.http.HttpUtils.AUTHORIZATION_HEADER;
import static com.vendo.security_lib.http.HttpUtils.BEARER_PREFIX;

@Configuration
@RequiredArgsConstructor
public class ProductRequestInterceptor {

    private final InternalGenerationPort internalGenerationPort;

    @Bean
    RequestInterceptor internalProductInfoInterceptor() {
        String token = internalGenerationPort.generate(ServiceName.PRODUCT_SERVICE);
        System.out.println(token);
        return request -> request.header(AUTHORIZATION_HEADER, BEARER_PREFIX + token);
    }

}
