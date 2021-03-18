package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午4:40:26
* 类说明
*/
@Data
public class GamePlayersClutches extends BaseModel {
	/**
	 * 玩家ID
	 */
	private Integer playerId;
	/**
	 * 玩家昵称
	 */
	private String nickname;
	
	/**
	 * Clutches 类型
	 */
	private String clutchesType;
	/**
	 * summary(JSON)
	 */
	private String summary ;
	/**
	 * clutches历史(JSON)
	 */
	private String clutcheshistory;
	/**
	 * 创建时间
	 */
	
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
