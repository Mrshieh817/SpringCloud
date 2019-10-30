package com.xcf.mybatis.Core;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月30日 上午11:32:02
* 类说明
*/
@Data
public class TeamMapsChild extends BaseModel {

	/**
	 * HLTV站点的团队id
	 */
	private Integer tltvteamId;
	/**
	 * 团队名称
	 */
	private String teamName;
	/**
	 * 地图类型id
	 */
	private Integer mapstypeId;
	/**
	 * 地图类型
	 */
	private String mapstypeName;
	/**
	 * 地域原始统计
	 */
	
	private String rawStats;
	
	/**
	 * Side breakdown
	 */	
	private String sideBreakdown;
	
	/**
	 * Game changers
	 */
	private String gameChangers;
	
	/**
	 * Matches
	 */	
	private String matches;
	
}
