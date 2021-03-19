package com.xcf.mybatis.Core;

import java.io.Serializable;

import javax.persistence.Transient;

import com.xcf.mybatis.Annotation.JsonRegularReplace;
import com.xcf.mybatis.Annotation.RegularReplaceEnum;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年9月6日 下午1:43:28
* 类说明
*/
@Data
public class SysUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	@JsonRegularReplace(contain=RegularReplaceEnum.VIP,groupRegular = "(\\d{3})(\\d{4})(\\d{4})",replacement = "$1****$3")
	private String pass;
	// 加了这注解，在mybatis映射数据库的时候，不会因为不存在的字段而报错
	@Transient
	@JsonRegularReplace(contain=RegularReplaceEnum.TOURIST,groupRegular = "(\\d{3})(\\d{4})(\\d{4})",replacement = "$1****$3")
	private String tel;

}
