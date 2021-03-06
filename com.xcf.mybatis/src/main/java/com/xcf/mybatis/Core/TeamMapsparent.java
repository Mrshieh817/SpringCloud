package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月30日 上午11:09:39
* 类说明
*/
@Data
public class TeamMapsparent extends BaseModel {
	/**
	 * HLTV站点的团队id
	 */
	private Integer teamId;
	/**
	 * 团队名称
	 */
	private String teamName;
	/**
	 * 地图分析(JSON)
	 */
	private String mapBreakdown;
	/**
	 * 比较突出的地图(JSON)
	 */
	private String mapHighlight;
	/**
	 * 地图概述(JSON)
	 */
	
	private String mapOverview;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
