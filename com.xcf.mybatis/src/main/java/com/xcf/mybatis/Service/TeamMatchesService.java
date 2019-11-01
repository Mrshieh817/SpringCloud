package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.TeamMatches;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月1日 下午4:17:10 类说明
 */

public interface TeamMatchesService {
	TeamMatches checkexist(TeamMatches model);

	Integer save(TeamMatches model);
}
