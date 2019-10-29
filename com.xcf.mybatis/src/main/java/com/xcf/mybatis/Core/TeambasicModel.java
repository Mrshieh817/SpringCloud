package com.xcf.mybatis.Core;

import java.math.BigDecimal;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月29日 上午10:09:35
* 类说明
*/
@Data
public class TeambasicModel {
	/**
	 * HLTV站点的团队id
	 */
	private Integer tltvteamId;
	/**
	 * 团队名称
	 */
	private String teamName;
	/**
	 * RATING能力值
	 */
	private Integer rating;
	/**
	 * 国旗
	 */
	private String nationimgUrl;
	
	/**
	 * 国家
	 */
	private String nation;
	
	/*
	 * 团队图案
	 */
	private String teamimgUrl;
	/**
	 * 地区
	 */
	private Integer region;//到时候用枚举
	/**
	 *累计金额
	 */
	private BigDecimal totalMoney;
	
	/**
	 * 最近匹配统计信息
	 */
	private String recentmatchesStatistics;
	/**
	 * 团队核心排名
	 */
	private String rankingCore;

}
