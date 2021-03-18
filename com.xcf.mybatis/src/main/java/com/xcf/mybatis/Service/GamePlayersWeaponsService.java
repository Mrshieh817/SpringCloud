package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayersWeapons;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月5日 下午1:10:16
* 类说明
*/

public interface GamePlayersWeaponsService {
	GamePlayersWeapons checkexist(GamePlayersWeapons model);
	Integer save(GamePlayersWeapons model);
}
