package com.xcf.mybatis.Core;

import javax.persistence.Transient;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年9月6日 下午1:43:28
* 类说明
*/
@Data
public class SysUser {
	private Integer id;
	private String name;
	private String pass;
	// 加了这注解，在mybatis映射数据库的时候，不会因为不存在的字段而报错
	@Transient
	private String tel;

}
