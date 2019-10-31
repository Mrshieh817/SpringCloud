package com.xcf.mybatis.Core;


import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月30日 上午8:58:23
* 团队的比赛信息
*/
@Data
public class TeamMatChes extends BaseModel {	
	/**
	 * HLTV站点的团队id
	 */
	private Integer tltvteamId;
	/**
	 * 团队名称
	 */
	private String teamName;
	/**
	 * 比赛时间
	 */
	private String date;
	/**
	 * 赛事
	 */
	private String Event;
	/**
	 * 对手
	 */
	private String Opponent;
	/**
	 * 地图
	 */
	private String Map;
	/**
	 * 结果
	 */
	private String Result;
	/**
	 * 输赢
	 */
	private String WL;

}
