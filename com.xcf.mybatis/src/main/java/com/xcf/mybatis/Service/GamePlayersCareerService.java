package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayersCareer;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月5日 上午11:06:40
* 类说明
*/

public interface GamePlayersCareerService {
	GamePlayersCareer checkexist(GamePlayersCareer model);
	Integer save(GamePlayersCareer model);
}
