package com.xcf.mybatis.Core;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月31日 下午4:23:01
* 类说明
*/
@Data
public class GamePlayersBasic extends BaseModel {
	/**
	 * 玩家ID
	 */
	private Integer playerId;
	/**
	 * 玩家昵称
	 */
	private String nickname;
	/**
	 * 玩家真实姓名
	 */
	private  String playerName;
	/**
	 * 玩家的照片
	 */
	private String playerImg;
	/**
	 * 年龄
	 */
	private String age;
	
	/**
	 * 现役团队ID
	 */
	private Integer teamId;
	/**
	 * 现役团队名称
	 */
	private String teamName;
	/**
	 * 现役团队的图标
	 */
	private String teamImg;
	/**
	 * 属于国家
	 */
	private String country;
	/**
	 * 国旗URL
	 */
	private String countryImg;
	/**
	 * 平均得分
	 */
	private String rating20;
	/**
	 * 每轮死亡
	 */
	private String dpr;
	/**
	 * 玩家杀死，助攻，幸存或被交易的回合百分比
	 */
	private String kast;
	/**
	 * 衡量多杀，开杀和抓紧所造成的影响
	 */
	private String impact;
	/**
	 * 每轮平均伤害
	 */
	private String adr;
	/**
	 * 每回合击杀数
	 */
	private String kpr;
	
	/**
	 * twitter地址
	 */
	private String twitter;
	/**
	 * twitch地址
	 */
	private String twitch;
	/**
	 * 成就(JSON)
	 */
	private String achiement;
	
	/**
	 * 统计（JSON）
	 */
	private String statistics;
	/**
	 * 队友(暂时不存，可以自己通过数据库关联查询)
	 */
	@Transient
	private String teammates;
	/**
	 * Form in filter(JSON)
	 */
	private String forminfilter;
	
	
	/**
	 * 创建时间
	 */
	
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
