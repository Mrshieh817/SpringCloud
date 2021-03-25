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
@Document(indexName = "ik_test",type = "_doc",useServerConfiguration = true,createIndex = false)
public class Elasticjianguanjia {
	
	@Id
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String version;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String defendant;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String company_uuid;
	
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String plaintiff;

}
