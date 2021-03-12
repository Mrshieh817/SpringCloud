package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayersMatches;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午5:47:58
* 类说明
*/

public interface GamePlayersMatchesService {
	GamePlayersMatches checkexist(GamePlayersMatches model);
	Integer save(GamePlayersMatches model);
}
