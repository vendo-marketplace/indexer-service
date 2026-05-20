package com.vendo.indexer_service.adapter.product.out.elasticsearch;

import com.vendo.indexer_service.adapter.product.out.elasticsearch.nested.ElasticAttribute;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(indexName = "products")
public final class ElasticProduct {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Integer)
    private Integer quantity;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Field(type = FieldType.Keyword)
    private String ownerId;

    @Field(type = FieldType.Keyword)
    private String categoryId;

    @Field(type = FieldType.Nested)
    private List<ElasticAttribute> attributes;

    @Field(type = FieldType.Boolean)
    private Boolean active;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

}
