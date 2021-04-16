package com.xcf.mybatis.Core;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author xcf
 * @Date 创建时间：2021年3月22日 上午11:34:50
 */
@Data
@Document(indexName = "ik_company_page", type = "_doc", useServerConfiguration = true, createIndex = false)
public class Elasticjianguanjia {

	@Id
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String regCapitalAmount;

	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String areaProvince;

	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String regLocation;

	@Field(type = FieldType.Text)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String crawled_time;

}
