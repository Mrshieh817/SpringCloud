package com.xcf.mybatis.Core;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
* @author xcf 
* @Date 创建时间：2021年3月18日 下午1:38:22 
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Param implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyword;
	
}