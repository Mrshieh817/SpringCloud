package com.xcf.mybatis.Controller;

/** 
* @author xcf 
* @Date 创建时间：2021年3月18日 上午11:37:26 
*/

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.xcf.mybatis.Core.ElasticUser;
import com.xcf.mybatis.Core.Elasticjianguanjia;
import com.xcf.mybatis.Core.Param;
import com.xcf.mybatis.Service.ElasticService;
import com.xcf.mybatis.Service.ElasticjianguanjiaService;
import com.xcf.mybatis.Tool.redis.RedisUtil;
import com.xcf.mybatis.aspect.WebLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/es")
@Api(value = "这是ES公共操作中心",tags = {"这是ES公共操作中心"})
@Slf4j
public class ElasticController {
	
	@Autowired
	RedisUtil redisutil;
	
	@Autowired
	private ElasticsearchRestTemplate elasticsearchTemplate;
	
	@Autowired
	private ElasticService elasticservice;
	
	@Autowired
	private ElasticjianguanjiaService elasticjianguanjiaservice;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("测试es的读取")
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
		buider.should(QueryBuilders.matchPhraseQuery("cityName", p.getKeyword()));
		searchSourceBuilder.query(buider);
		
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		//queryBuilder.withIds(ids)

		System.out.println(searchSourceBuilder.toString());

		// 第一个参数是页数page，第二个参数是每页数据数量pageSize
		Pageable pageable = PageRequest.of(0, 400);
		Page<Map<String, Object>> page = (Page) elasticjianguanjiaservice.search(searchSourceBuilder.query(), pageable);

		timeStopWatch.stop();
		double tt = timeStopWatch.getTotalTimeSeconds();
		System.out.println("用时:" + tt);
		return page;
	}
	
	@ApiOperation("测试es的读取")
	@RequestMapping("/test1")
	public Object test1(@RequestBody Param p) {
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.trackTotalHits(true);
		
		// 设置查询调减方法
		BoolQueryBuilder buider = QueryBuilders.boolQuery();
		// 根据条件查
		buider.should(QueryBuilders.matchPhraseQuery("cityName", p.getKeyword()));
		searchSourceBuilder.query(buider);
		
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		
		Pageable pageable = PageRequest.of(0, 400);
		queryBuilder.withPageable(pageable);
		
		queryBuilder.withQuery(searchSourceBuilder.query());
		System.out.println(searchSourceBuilder.toString());

		SearchHits<Elasticjianguanjia> pagesPage= elasticsearchTemplate.search(queryBuilder.build(), Elasticjianguanjia.class);
		
		return pagesPage;
	}
	
	
	//测试Field字段属性反射
	@RequestMapping("/get")
	@ApiOperation("测试Field字段属性反射")
	public void get(@RequestParam(value = "name",defaultValue = "baba")String name ) throws IllegalArgumentException, IllegalAccessException {
		 //实体类 信息
		 ElasticUser model=new ElasticUser(10,name,"重庆","90") ;
		 //拿取被反射对象的基本信息，如字段注解，字段名称和它的值
		 Field[] fields=model.getClass().getDeclaredFields();  
		 for (int i = 0; i < fields.length; i++) {
			 //setAccessible的作用是将类中的成员变量private变成可访问变量,然后获取里面value,故必须进行此操
			 fields[i].setAccessible(true);
			 //获取当前对象是否包含有Annotation相关的自定义注解
			 WebLog annotatedFields= fields[i].getDeclaredAnnotation(WebLog.class);
			 String description="";
			 String valueString="";
			 //判断当前对象是否有自定义注解
			 if (Objects.nonNull(annotatedFields)) {
				 //获取注解上的信息
				  description= annotatedFields.description();
				  valueString=annotatedFields.value();
			 }
			 String dfString= fields[i].getName();
			 Object ob= fields[i].get(model);
			 System.out.println("object name:"+dfString+"===object value:"+ob+"====description:"+description+"=====valueString:"+valueString);
		}
	
	}
	
	/**
	 * ceshi 
	 * @return
	 */
	@RequestMapping("/redis")
	public Object redis() {
		log.info("hahahaaaaaaaaaaaaaaaa~~~~~~~~");
		redisutil.set("fff", "mr shieh");
		return "success";
	}

}
