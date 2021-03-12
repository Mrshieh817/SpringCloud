package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.TeamMapschild;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月1日 下午5:37:18
* 类说明
*/

public interface TeamMapschildService {
	TeamMapschild checkexist(TeamMapschild model);
	Integer save(TeamMapschild model);
}
