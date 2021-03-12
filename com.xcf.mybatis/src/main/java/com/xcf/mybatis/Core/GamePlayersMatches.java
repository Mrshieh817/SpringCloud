package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午4:21:25
* 类说明
*/
@Data
public class GamePlayersMatches extends BaseModel {
	/**
	 * 玩家ID
	 */
	private Integer playerId;
	/**
	 * 玩家昵称
	 */
	private String nickname;

	/**
	 * 得分统计(JSON)
	 */
	private String summary;
	/**
	 * 比赛历史统计(JSON)
	 */
	private String matchhistory;
	/**
	 * 创建时间
	 */
	
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
