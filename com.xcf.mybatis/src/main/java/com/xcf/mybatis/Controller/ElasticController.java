package com.xcf.mybatis.Controller;

/** 
* @author xcf 
* @Date 创建时间：2021年3月18日 上午11:37:26 
*/

import java.io.Serializable;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xcf.mybatis.Core.ElasticUser;
import com.xcf.mybatis.Core.Param;
import com.xcf.mybatis.Service.ElasticService;
import com.xcf.mybatis.Service.ElasticjianguanjiaService;

@RestController
@RequestMapping("/es")
public class ElasticController {
	@Autowired
	private ElasticService elasticservice;
	
	@Autowired
	private ElasticjianguanjiaService elasticjianguanjiaservice;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/test")
	public Object test(@RequestBody Param p) {
		StopWatch timeStopWatch = new StopWatch();
		timeStopWatch.start();

		// 用于分页是时候出现出真实的命中返回数据
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.trackTotalHits(true);

		// 设置查询调减方法
		BoolQueryBuilder buider = QueryBuilders.boolQuery();
		// 根据条件查
		buider.should(QueryBuilders.matchPhraseQuery("defendant", p.getKeyword()));
		searchSourceBuilder.query(buider);

		System.out.println(searchSourceBuilder.toString());

		// 第一个参数是页数page，第二个参数是每页数据数量pageSize
		Pageable pageable = PageRequest.of(0, 10);
		Page<Map<String, Object>> page = (Page) elasticjianguanjiaservice.search(searchSourceBuilder.query(), pageable);

		timeStopWatch.stop();
		double tt = timeStopWatch.getTotalTimeSeconds();
		System.out.println("用时:" + tt);
		return page;
	}

}
