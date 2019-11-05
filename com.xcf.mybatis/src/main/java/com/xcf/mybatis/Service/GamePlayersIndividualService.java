package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayersIndividual;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月5日 上午10:13:49
* 类说明
*/

public interface GamePlayersIndividualService {
	GamePlayersIndividual checkexist(GamePlayersIndividual model);

	Integer save(GamePlayersIndividual model);
}
