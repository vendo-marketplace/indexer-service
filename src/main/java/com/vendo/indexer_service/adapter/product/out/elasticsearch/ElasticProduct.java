package com.vendo.indexer_service.adapter.product.out.elasticsearch;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "products")
public class ElasticProduct {

    private String id;

}
