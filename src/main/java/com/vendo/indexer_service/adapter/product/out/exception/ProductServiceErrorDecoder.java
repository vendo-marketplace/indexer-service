package com.vendo.indexer_service.adapter.product.out.exception;

import com.vendo.core_lib.type.ServiceName;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductServiceErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
            return new ProductServiceUnavailableException(ServiceName.PRODUCT_SERVICE + " is unavailable.");
        }

        log.error(response.toString());
        return new IllegalArgumentException("Unhandled product exception.");
    }

}
