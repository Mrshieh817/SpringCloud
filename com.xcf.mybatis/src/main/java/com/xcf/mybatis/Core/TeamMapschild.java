package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月30日 上午11:32:02
* 类说明
*/
@Data
public class TeamMapschild extends BaseModel {

	/**
	 * HLTV站点的团队id
	 */
	private Integer teamId;
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
	 * 地域原始统计(JSON)
	 */
	
	private String rawStats;
	
	/**
	 * Side breakdown(JSON)
	 */	
	private String sideBreakdown;
	
	/**
	 * Game changers(JSON)
	 */
	private String gameChangers;
	
	/**
	 * Matches(JSON)
	 */	
	private String matches;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
}
