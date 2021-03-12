package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayersWeapons;
import com.xcf.mybatis.Mapper.GamePlayersWeaponsMapper;
import com.xcf.mybatis.Service.GamePlayersWeaponsService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月5日 下午1:10:57 类说明
 */
@Service
public class GamePlayersWeaponsServiceimpl implements GamePlayersWeaponsService {
	@Autowired
	private GamePlayersWeaponsMapper gamePlayersWeaponsMapper;

	/**
	 * 验证是否存在
	 */
	@Override
	public GamePlayersWeapons checkexist(GamePlayersWeapons model) {
		GamePlayersWeapons search = new GamePlayersWeapons();
		if (model != null && model.getPlayerId() != null) {
			search.setPlayerId(model.getPlayerId());
		}
		return gamePlayersWeaponsMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(GamePlayersWeapons model) {
		try {
			if (model.getId() != null) {
				return gamePlayersWeaponsMapper.updateByPrimaryKeySelective(model);
			} else {
				return gamePlayersWeaponsMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println("玩家:" + model.getNickname() + "====" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}

}
