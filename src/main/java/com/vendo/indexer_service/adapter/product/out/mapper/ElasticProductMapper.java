package com.vendo.indexer_service.adapter.product.out.mapper;

import com.vendo.indexer_service.adapter.product.out.elasticsearch.ElasticProduct;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.infrastructure.mapper.MapStructConfig;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapStructConfig.class)
public interface ElasticProductMapper {

    ElasticProduct toEntity(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ElasticProduct entity, Product product);

}
