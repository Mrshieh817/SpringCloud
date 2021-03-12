package com.xcf.mybatis.Service;

import com.xcf.mybatis.Core.GamePlayersBasic;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午2:46:16
* 类说明
*/

public interface GamePlayersBasicService {
	GamePlayersBasic checkexist(GamePlayersBasic model);
	Integer save(GamePlayersBasic model);
}
