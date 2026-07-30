package com.vendo.indexer_service.adapter.product.out.mapper;

import com.vendo.indexer_service.adapter.product.out.elasticsearch.ElasticProduct;
import com.vendo.indexer_service.adapter.product.out.elasticsearch.nested.ElasticAddress;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.domain.product.nested.Address;
import com.vendo.indexer_service.infrastructure.config.MapStructConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface ElasticProductMapper {

    ElasticProduct toEntity(Product product);

    @Mapping(target = "images", source = "imageKeys", qualifiedByName = "buildUrls")
    ElasticProduct toEntity(Product product, @Context String baseUrl);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "images", source = "imageKeys", qualifiedByName = "buildUrls")
    void updateEntity(
            @MappingTarget ElasticProduct entity,
            Product product,
            @Context String baseUrl
    );

    ElasticAddress toEntity(Address address);

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
