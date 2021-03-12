package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年10月31日 下午2:20:02 类说明
 */
@Data
public class GamePlayers extends BaseModel {
	/**
	 * 玩家id
	 */
	private Integer playerId;

	/**
	 * 玩家姓名
	 */
	private String playerName;
	/**
	 * 属于国家
	 */
	private String country;
	/**
	 * 国旗URL
	 */
	private String countryImg;
	/**
	 * 团队名称(存储团队的名称，图片，id)(JSON)
	 */
	private String teamName;
	/**
	 * 刷的地图
	 */
	private String maps;
	/**
	 * 杀敌和死亡差异
	 */	
	private String killdeathDiff;
	/**
	 * 杀敌和死亡比例
	 */
	
	private String killdeathScale;
	/**
	 * 评分
	 */
	private String rating;
	/**
	 * 创建时间
	 */
	
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
