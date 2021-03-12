package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayers;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 上午11:04:46
* 类说明
*/

public interface GamePlayersService {
	GamePlayers checkexist(GamePlayers model);
	Integer save(GamePlayers model);
}
