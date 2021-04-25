package com.xcf.mybatisplus.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午2:31:00 
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(message = "id不能为空")
	private Integer id;
	private String name;
	private String pass;
	private Date updateTime;
}
