package com.xcf.mybatis.Core;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.xcf.mybatis.aspect.WebLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
* @author xcf 
* @Date 创建时间：2021年3月18日 上午11:25:54 
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "xcfin",type = "_doc",useServerConfiguration = true,createIndex = false)
public class ElasticUser {
	@Id
	private Integer id;
	
	/**
	 * @Field 是ELASTICSEARCH搜索引擎的注解,主要用来指定查询实体字段是什么类型,并且这个字段是用什么查询分词器
	 * @WebLog 自定义的注解,可以用在方法或者实体字段上,然后通过AOP或者中间件方式Field反射getDeclaredFields，getDeclaredAnnotation来获取到信息
	 */
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	@WebLog(description = "我就测试一下description")
	private String title;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	@WebLog(value  = "我就测试一下value")
	private String director;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String year;

}
