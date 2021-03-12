package com.xcf.mybatis.Core;

import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午4:32:57
* 职业生涯
*/
@Data
public class GamePlayersCareer extends BaseModel {
	/**
	 * 玩家ID
	 */
	private Integer playerId;
	/**
	 * 玩家昵称
	 */
	private String nickname;
	/**
	 * 职业生涯评分(JSON)
	 */
	private String careerrating ;
	/**
	 * 职业生涯导图(JSON)
	 */
	private String careerform;
	/**
	 * 创建时间
	 */
	
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
