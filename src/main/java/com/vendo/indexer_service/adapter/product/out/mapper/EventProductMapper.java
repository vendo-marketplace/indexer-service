package com.vendo.indexer_service.adapter.product.out.mapper;

import com.vendo.indexer_service.adapter.product.in.kafka.dto.ProductCreatedEvent;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.infrastructure.mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface EventProductMapper {

    Product toProduct(ProductCreatedEvent event);

}
