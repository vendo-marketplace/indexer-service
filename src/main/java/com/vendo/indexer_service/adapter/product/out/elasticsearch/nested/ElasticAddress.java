package com.vendo.indexer_service.adapter.product.out.elasticsearch.nested;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

public class ElasticAddress {
    @Field(type = FieldType.Keyword)
    private String region;
    @Field(type = FieldType.Keyword)
    private String city;

    @GeoPointField
    private ElasticLocation location;

    public static class ElasticLocation {
        private double lat;
        private double lon;
    }
}



