package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayersClutches;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月5日 下午1:54:47
* 类说明
*/

public interface GamePlayersClutchesService {
	GamePlayersClutches checkexist(GamePlayersClutches model);
	Integer save(GamePlayersClutches model);
}
