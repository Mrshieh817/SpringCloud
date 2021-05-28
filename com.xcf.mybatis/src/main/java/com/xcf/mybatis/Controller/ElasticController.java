package com.xcf.mybatis.Controller;

/** 
* @author xcf 
* @Date 创建时间：2021年3月18日 上午11:37:26 
*/

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
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
import com.xcf.mybatis.aspect.AccessLimit;
import com.xcf.mybatis.aspect.WebLog;

import cn.hutool.core.collection.CollUtil;
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
	
	/**
	 * ResissionClient
	 */
	@Autowired
	private RedissonClient redissonClient;

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
	public Object test1(@RequestBody Param p) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.trackTotalHits(true);
		
		// 设置查询调减方法
		BoolQueryBuilder buider = QueryBuilders.boolQuery();
		buider.must(QueryBuilders.matchQuery("projectName", p.getKeyword()));
		//buider.must(
				//  QueryBuilders.boolQuery()
				//.must(QueryBuilders.rangeQuery("bid_time").lt(sdf.parse("2021-05-06 16:00:00.000")))
				//.must(QueryBuilders.rangeQuery("bid_time").gt(sdf.parse("2010-01-01 16:00:00.000")))
				//);
		//buider.must(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("id", ("14615").split(","))));
		
		//buider.must(QueryBuilders.rangeQuery("contractMaxDate").lte("2021-04-16 09:20:00")).m;
		// 根据条件查
		//buider.should(QueryBuilders.matchPhraseQuery("cityName", p.getKeyword()));
		searchSourceBuilder.query(buider);
		
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		
		Pageable pageable = PageRequest.of(0, 10);
		queryBuilder.withPageable(pageable);
		queryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));
		queryBuilder.withQuery(searchSourceBuilder.query());
		
		 //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置高亮字段
        highlightBuilder.field("projectName");
        //如果要多个字段高亮,这项要为false
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
      //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等   
      	highlightBuilder.fragmentSize(800000); //最大高亮分片数
        highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段
        

        queryBuilder.withHighlightBuilder(highlightBuilder);
		System.out.println(searchSourceBuilder.toString());

		
		//调用公共方法
	    List<Elasticjianguanjia>	list= commomPage(queryBuilder.build(),Elasticjianguanjia.class);
		
	
		return list;
	}
	
	
	private <T> List<T> commomPage(Query query, Class<T> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException, NoSuchMethodException{
		//查询ES
		SearchHits<T> pagesPage= elasticsearchTemplate.search(query, clazz);
		//转换es的结果为自己写想要的数据类型
		List<T> list=pagesPage.stream().map(org.springframework.data.elasticsearch.core.SearchHit::getContent).collect(Collectors.toList());
		//获取es返回设置的高亮数据字段
		List<Map<String, List<String>>> heightlist=pagesPage.stream().map(org.springframework.data.elasticsearch.core.SearchHit::getHighlightFields).collect(Collectors.toList());
		//循环es返回的主要数据
		for (int i = 0; i < list.size(); i++) {
			//获取list集合里面单个对象的信息
			T jianmo=list.get(i);
			//获取高亮查询结果的单个对象信息
			Map<String, List<String>> hmpMap=heightlist.get(i);
			if (CollUtil.isEmpty(hmpMap)) {
				continue;
			}
			for(Map.Entry<String, List<String>> entry : hmpMap.entrySet()) {
				//拼接高亮对象字符信息,用于对es主体返回的信息进行更改
				String keyString="set"+entry.getKey().substring(0,1).toUpperCase()+entry.getKey().substring(1);
				//拼接高亮对象字符信息,用于对es主体返回的信息进行更改
				String realykeyString="set"+entry.getKey().substring(0,1).toUpperCase()+entry.getKey().substring(1)+"realy";
				//获取当前循环体的值
				String vaString=entry.getValue().get(0);
				//调用jianmo对象的方法
				Method method=jianmo.getClass().getMethod(keyString, String.class);
				//调用jianmo对象的方法
				//Method realymethod=jianmo.getClass().getMethod(realykeyString, String.class);
				//执行jianmo对象的方法
				//realymethod.invoke(jianmo, vaString.replace("<span style='color:red'>", "").replace("</span>", ""));
				//调用jianmo对象的方法
				method.invoke(jianmo, vaString);
				
			}
		}
		
		return list;
	}
	
	//测试Field字段属性反射
	@RequestMapping("/get")
	@ApiOperation("测试Field字段属性反射")
	public void get(@RequestParam(value = "name",defaultValue = "baba")String name ) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException, NoSuchFieldException {
		 //实体类 信息
		 ElasticUser model=new ElasticUser(10,"","重庆","90") ;
		 //拿取被反射对象的基本信息，如字段注解，字段名称和它的值
		 Field[] fields=model.getClass().getDeclaredFields();  

		 for (int i = 0; i < fields.length; i++) {
			 //setAccessible的作用是将类中的成员变量private变成可访问变量,然后获取里面value,故必须进行此操
			 fields[i].setAccessible(true);
			 //获取当前对象是否包含有Annotation相关的自定义注解
			 WebLog annotatedFields= fields[i].getDeclaredAnnotation(WebLog.class);
			 String description="";
			 String valueString="";
			 
			 //获取当前没反射字段的类型
			 Class typeClass= fields[i].getType();
			 //拼接被执行实体某个字段的set属性值,然后赋值给方法，进行更改
			 String nameString="set"+fields[i].getName().substring(0, 1).toUpperCase()+fields[i].getName().substring(1);
			 Object vvvvString=fields[i].get(model);
			 if (ObjectUtils.isEmpty(vvvvString)) {
				//获取当前实体某个值的set方法
				 Method mtMethod1=model.getClass().getMethod(nameString, typeClass);
				 //执行方法,进行赋值
				 mtMethod1.invoke(model,typeClass==Integer.class?11111:vvvvString+"我是文本被更该了后补加的(值为空)");
			}
			 //判断当前对象是否有自定义注解
			 if (Objects.nonNull(annotatedFields)) {
				 //获取注解上的信息,通过代理的方式更改注解信息
				 InvocationHandler h = Proxy.getInvocationHandler(annotatedFields);
				 Field fileddescription= h.getClass().getDeclaredField("memberValues");
				  fileddescription.setAccessible(true);
				  Map memberValues = null;
				  memberValues = (Map) fileddescription.get(h);
				  memberValues.put("description", "我把你的description统一改了");
				  //获取注解的信息
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
	
	@RequestMapping("/testarry")
	@AccessLimit(limit = 5,sec = 5,disEl = "#root[p].keyword")
	public String testarry(@RequestBody Param p) {
		// 测试Redisson的桶实例  开始
		RBucket<String> testrbucket=redissonClient.getBucket("onlykey");
		String kString= testrbucket.get();
		if (ObjectUtils.isEmpty(kString)) {
			testrbucket.set("是我给redis桶里面写的值", 60, TimeUnit.SECONDS);
		}else {
			System.out.println("redis桶里面的值:"+kString);
		}
		// 结束
		List<String> list=new ArrayList<>();
		 String er="a,b,c,d";
		Arrays.stream(er.split(",")).forEach((action)->{
			list.add(action);
		});
		return list.toString();
	}

}
