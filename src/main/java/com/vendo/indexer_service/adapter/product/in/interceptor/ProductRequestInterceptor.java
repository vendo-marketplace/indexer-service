package com.vendo.indexer_service.adapter.product.in.interceptor;

import com.vendo.core_lib.type.ServiceName;
import com.vendo.indexer_service.adapter.security.out.jwt.InternalTokenGenerationPort;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.vendo.security_lib.constants.AuthConstants.AUTHORIZATION_HEADER;
import static com.vendo.security_lib.constants.AuthConstants.BEARER_PREFIX;

@Configuration
@RequiredArgsConstructor
public class ProductRequestInterceptor {

    private final InternalTokenGenerationPort internalTokenGenerationPort;

    @Bean
    RequestInterceptor internalProductInfoInterceptor() {
        return request -> request.header(AUTHORIZATION_HEADER, BEARER_PREFIX + internalTokenGenerationPort.generate(ServiceName.PRODUCT_SERVICE));
    }

}
