package com.vendo.indexer_service.adapter.product.out.elasticsearch.nested;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class ElasticAddress {

    @Field(type = FieldType.Keyword)
    private String city;

}



