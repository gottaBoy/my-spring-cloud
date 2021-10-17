package com.mysting.tomato.es.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

/**
 * nginx日志对象,映射es中的索引kafka_nginxlogs-*
 */
@Data
@Document(indexName = "kafka_nginxlogs-*", type = "_doc" )
public class NinxLogDocument {
    @Id
    private String id;
    private String lon ;
    private String lat ;
    
}