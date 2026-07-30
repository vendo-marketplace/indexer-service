package com.vendo.indexer_service.adapter.product.out.elasticsearch.nested;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

@Data
public class ElasticAddress {
    @Field(type = FieldType.Keyword)
    private String region;
    @Field(type = FieldType.Keyword)
    private String city;

    @GeoPointField
    private ElasticLocation location;

    @Data
    public static class ElasticLocation {
        private double lat;
        private double lon;
    }
}



