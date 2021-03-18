package com.xcf.mybatis.Core;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月1日 下午4:32:41
* 类说明
*/
@Data
public class Matches {
	/**
	 * 比赛时间
	 */
	private String date;
	/**
	 * 赛事
	 */
	private String event;
	/**
	 * 对手
	 */
	private String opponent;
	/**
	 * 地图
	 */
	private String map;
	/**
	 * 结果
	 */
	private String result;
	/**
	 * 输赢
	 */
	private String wl;
}
