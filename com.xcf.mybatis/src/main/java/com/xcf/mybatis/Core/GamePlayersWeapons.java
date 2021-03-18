package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午4:37:04
* 武器装备
*/
@Data
public class GamePlayersWeapons extends BaseModel {
	/**
	 * 玩家ID
	 */
	private Integer playerId;
	/**
	 * 玩家昵称
	 */
	private String nickname;
	/**
	 * 图(JSON)
	 */
	private String graph ;
	/**
	 * 扩展武器概述(JSON)
	 */
	private String extendedweaponoverview;
	/**
	 * 创建时间
	 */
	
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
