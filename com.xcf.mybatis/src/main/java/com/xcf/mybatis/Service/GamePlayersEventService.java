package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayersEvent;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月5日 上午10:44:21
* 类说明
*/

public interface GamePlayersEventService {
	GamePlayersEvent checkexist(GamePlayersEvent model);
	Integer save(GamePlayersEvent model);
}
