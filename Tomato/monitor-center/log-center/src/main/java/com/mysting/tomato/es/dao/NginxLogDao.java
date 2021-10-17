package com.mysting.tomato.es.dao;

import com.mysting.tomato.es.entity.NinxLogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * ELK收集nginx中的日志查询接口
 */
@Repository
public interface NginxLogDao extends ElasticsearchRepository<NinxLogDocument, String> {

}