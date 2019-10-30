package com.xcf.mybatis.Core;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月30日 上午11:09:39
* 类说明
*/
@Data
public class TeamMapsParent extends BaseModel {
	/**
	 * HLTV站点的团队id
	 */
	private Integer tltvteamId;
	/**
	 * 团队名称
	 */
	private String teamName;
	/**
	 * 地图分析
	 */
	private String mapBreakdown;
	/**
	 * 比较突出的地图
	 */
	private String mapHighlight;
	/**
	 * 地图概述
	 */
	
	private String mapOverview;

}
