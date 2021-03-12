package com.xcf.mybatis.Core;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月29日 上午10:09:35
* 类说明
*/
@Data
public class TeamBasic extends BaseModel {	
	/**
	 * HLTV站点的团队id
	 */
	private Integer teamId;
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
	 * 世界排名
	 */
	
	private String worldRanking;
	
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
	 * 地区排名
	 */
	private String regionRanking;
	/**
	 *累计金额
	 */
	private BigDecimal totalMoney;
	/**
	 * 奖杯，存储json
	 */
	
	private String trophy;
	/**
	 * 队员存储json
	 */
	
	private String palyers;
	
	/**
	 * 最近匹配统计信息(JSON)
	 */
	private String recentmatchesStatistics;
	/**
	 * 团队核心排名(JSON)
	 */
	private String rankingCore;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
