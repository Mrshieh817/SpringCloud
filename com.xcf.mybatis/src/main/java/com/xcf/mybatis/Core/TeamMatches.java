package com.xcf.mybatis.Core;


import java.util.Date;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月30日 上午8:58:23
* 团队的比赛信息
*/
@Data
public class TeamMatches extends BaseModel {	
	/**
	 * HLTV站点的团队id
	 */
	private Integer teamId;
	/**
	 * 团队名称
	 */
	private String teamName;
	/**
	 * 比赛数据
	 */
	
	private String matchesdata;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
