package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayersEvent;
import com.xcf.mybatis.Mapper.GamePlayersEventMapper;
import com.xcf.mybatis.Service.GamePlayersEventService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月5日 上午10:45:04 类说明
 */
@Service
public class GamePlayersEventServiceimpl implements GamePlayersEventService {
	@Autowired
	private GamePlayersEventMapper gamePlayersEventMapper;

	/**
	 * 验证是否存在
	 */
	@Override
	public GamePlayersEvent checkexist(GamePlayersEvent model) {
		GamePlayersEvent search = new GamePlayersEvent();
		if (model != null && model.getPlayerId() != null) {
			search.setPlayerId(model.getPlayerId());
		}
		return gamePlayersEventMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(GamePlayersEvent model) {
		try {
			if (model.getId() != null) {
				return gamePlayersEventMapper.updateByPrimaryKeySelective(model);
			} else {
				return gamePlayersEventMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println("玩家:" + model.getNickname() + "====" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}
}
