package com.xcf.mybatis.Core;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/** 
* @author xcf 
* @Date 创建时间：2021年3月22日 上午11:34:50 
*/
@Data
@Document(indexName = "ik_siku_page",type = "_doc",useServerConfiguration = true,createIndex = false)
public class Elasticjianguanjia {
	
	@Id
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String projectNo;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String cityName;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String constructeCompany;
	
	@Field(type = FieldType.Date,analyzer = "ik_max_word")
	private String contractMaxDate;

}
