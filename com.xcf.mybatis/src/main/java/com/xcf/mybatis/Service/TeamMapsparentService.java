package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.TeamMapsparent;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月1日 下午5:11:00
* 类说明
*/

public interface TeamMapsparentService {
	TeamMapsparent checkexist(TeamMapsparent model);
	Integer save(TeamMapsparent model);

}
