package com.xcf.mybatisplus.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
* @author xcf 
* @Date 创建时间：2021年5月24日 下午3:46:01 
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统热配置表参数Dto")
public class KernelHotConfigDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4070089867730644639L;
	
	/**
	 * 热配置编号
	 */
	@TableField(value = "Config_ID")
	@ApiModelProperty("热配置编号")
	private Integer configID;
	
	
	/**
	 * 热配置键
	 */
	@TableField(value = "Config_Key")
	@NotBlank(message = "热配置键不能为空!")
	@ApiModelProperty("热配置键")
	private String configKey;
	
	
	/**
	 * 热配置值
	 */
	@TableField(value = "Config_Value")
	@ApiModelProperty("热配置值")
	private String configValue;
	
	/**
	 * 是否启用 (0-否; 1-是)
	 */
	@TableField(value ="Config_Enable")
	@ApiModelProperty("是否启用 (0-否; 1-是)")
	private Integer configEnable;
	
	/**
	 * 描述信息
	 */
	@TableField(value ="Config_Description")
	@ApiModelProperty("描述信息")
	private String configDescription;
	
	/**
	 * 自定义标记
	 */
	@TableField(value ="Config_Flag")
	@ApiModelProperty("自定义标记")
	private String configFlag;
	

}