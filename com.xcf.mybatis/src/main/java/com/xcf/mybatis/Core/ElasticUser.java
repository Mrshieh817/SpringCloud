package com.xcf.mybatis.Core;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/** 
* @author xcf 
* @Date 创建时间：2021年3月18日 上午11:25:54 
*/
@Data
@Document(indexName = "xcfin",type = "_doc",useServerConfiguration = true,createIndex = false)
public class ElasticUser {
	@Id
	private Integer id;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String title;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String director;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String year;

}
