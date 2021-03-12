package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayersOpponents;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月5日 下午2:57:38
* 类说明
*/

public interface GamePlayersOpponentsService {
	GamePlayersOpponents checkexist(GamePlayersOpponents model);
	Integer save(GamePlayersOpponents model);
}
