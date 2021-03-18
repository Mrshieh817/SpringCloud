package com.xcf.mybatis.Controller;
/** 
* @author xcf 
* @Date 创建时间：2021年3月18日 上午11:37:26 
*/

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xcf.mybatis.Core.ElasticUser;
import com.xcf.mybatis.Core.Param;
import com.xcf.mybatis.Service.ElasticService;

@RestController
@RequestMapping("/es")
public class ElasticController {
	@Autowired
    private	ElasticService elasticservice;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/test")
	public Object test(@RequestBody Param p) {
		BoolQueryBuilder buider=QueryBuilders.boolQuery();
		//根据条件查
		buider.should(QueryBuilders.matchPhraseQuery("title", p.getKeyword()));
		System.out.println(buider.toString());
		Page<ElasticUser> page= (Page)elasticservice.search(buider);
		return page;
	}

}
