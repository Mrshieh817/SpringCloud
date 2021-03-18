package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayersIndividual;
import com.xcf.mybatis.Mapper.GamePlayersIndividualMapper;
import com.xcf.mybatis.Service.GamePlayersIndividualService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月5日 上午10:14:25 类说明
 */
@Service
public class GamePlayersIndividualServiceimpl implements GamePlayersIndividualService {
	@Autowired
	private GamePlayersIndividualMapper gamePlayersIndividualMapper;

	/**
	 * 验证是否存在
	 */
	@Override
	public GamePlayersIndividual checkexist(GamePlayersIndividual model) {
		GamePlayersIndividual search = new GamePlayersIndividual();
		if (model != null && model.getPlayerId() != null) {
			search.setPlayerId(model.getPlayerId());
		}
		return gamePlayersIndividualMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(GamePlayersIndividual model) {
		try {
			if (model.getId() != null) {
				return gamePlayersIndividualMapper.updateByPrimaryKeySelective(model);
			} else {
				return gamePlayersIndividualMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println("玩家:" + model.getNickname() + "====" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}
}
