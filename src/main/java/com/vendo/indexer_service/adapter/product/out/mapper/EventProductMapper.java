package com.vendo.indexer_service.adapter.product.out.mapper;

import com.vendo.event_lib.product.ProductCreatedEvent;
import com.vendo.event_lib.product.ProductUpdatedEvent;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.infrastructure.config.MapStructConfig;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface EventProductMapper {

    @Mapping(target = "images", source = "imageKeys", qualifiedByName = "buildUrls")
    Product toProduct(ProductCreatedEvent event, @Context String baseUrl);

    Product toProduct(ProductUpdatedEvent event);

    @Named("buildUrls")
    default List<String> buildUrls(List<String> imageKeys, @Context String baseUrl) {
        if (imageKeys == null) {
            return null;
        }

        return imageKeys.stream()
                .map(key -> baseUrl + key)
                .toList();
    }

}
