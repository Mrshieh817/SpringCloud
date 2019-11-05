package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayersClutches;
import com.xcf.mybatis.Mapper.GamePlayersClutchesMapper;
import com.xcf.mybatis.Service.GamePlayersClutchesService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月5日 下午1:55:30 类说明
 */
@Service
public class GamePlayersClutchesServiceimpl implements GamePlayersClutchesService {
	@Autowired
	private GamePlayersClutchesMapper gamePlayersClutchesMapper;

	/**
	 * 验证是否存在
	 */
	@Override
	public GamePlayersClutches checkexist(GamePlayersClutches model) {
		GamePlayersClutches search = new GamePlayersClutches();
		if (model != null && model.getPlayerId() != null) {
			search.setPlayerId(model.getPlayerId());
		}
		if (model != null && model.getClutchesType() != null && model.getClutchesType()!="") {
			search.setClutchesType(model.getClutchesType());
		}
		return gamePlayersClutchesMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(GamePlayersClutches model) {
		try {
			if (model.getId() != null) {
				return gamePlayersClutchesMapper.updateByPrimaryKeySelective(model);
			} else {
				return gamePlayersClutchesMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println("玩家:" + model.getNickname() + "====" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}

}
