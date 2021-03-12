package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.TeamBasic;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月1日 下午2:40:32 类说明
 */

public interface TeambasicService {
	TeamBasic checkexsit(TeamBasic model);

	Integer save(TeamBasic model);
}
