package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月4日 下午4:01:04 个人战绩
 */
@Data
public class GamePlayersIndividual extends BaseModel {
	/**
	 * 玩家ID
	 */
	private Integer playerId;
	/**
	 * 玩家昵称
	 */
	private String nickname;

	/**
	 * 总体统计(JSON)
	 */
	private String overallstats;
	/**
	 * 回合统计(JSON)
	 */
	private String roundstats;
	/**
	 * 开场统计(JSON)
	 */
	private String openingstats;
	/**
	 * 武器统计(JSON)
	 */
	private String weaponstats;
	/**
	 * 创建时间
	 */
	
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
