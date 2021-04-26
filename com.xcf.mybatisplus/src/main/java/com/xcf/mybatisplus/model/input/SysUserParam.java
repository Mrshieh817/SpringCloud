package com.xcf.mybatisplus.model.input;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import com.xcf.mybatisplus.model.Vo.PageAdapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** 
* @author xcf 
* @Date 创建时间：2021年4月26日 上午9:36:46 
*/
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SysUserParam extends PageAdapter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5750227837632331410L;
	@NotBlank(message = "id不能为空")
	private String id;
	private String name;
	private String pass;
	private Date updateTime;

}
