package com.vendo.indexer_service.adapter.product.out.mapper;

import com.vendo.event_lib.product.ProductCreatedEvent;
import com.vendo.event_lib.product.ProductUpdatedEvent;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.infrastructure.mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface EventProductMapper {

    Product toProduct(ProductCreatedEvent event);

    Product toProduct(ProductUpdatedEvent event);

}
