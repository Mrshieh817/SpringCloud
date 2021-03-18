package com.xcf.mybatis.Service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.xcf.mybatis.Core.ElasticUser;

/** 
* @author xcf 
* @Date 创建时间：2021年3月18日 上午11:38:07 
*/
@Component
public interface ElasticService extends ElasticsearchRepository<ElasticUser,Integer>{

}
