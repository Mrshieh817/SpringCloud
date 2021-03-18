package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午4:43:57
* 对手
*/
@Data
public class GamePlayersOpponents extends BaseModel {

	/**
	 * 玩家ID
	 */
	private Integer playerId;
	/**
	 * 玩家昵称
	 */
	private String nickname;
	
	/**
	 * 对手 类型
	 */
	private String opponentsType;
	/**
	 * Opponents(JSON)
	 */
	private String opponents;
	/**
	 * 创建时间
	 */
	
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
