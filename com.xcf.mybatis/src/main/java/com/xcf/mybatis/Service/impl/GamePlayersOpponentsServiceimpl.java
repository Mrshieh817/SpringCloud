package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayersMatches;
import com.xcf.mybatis.Core.GamePlayersOpponents;
import com.xcf.mybatis.Mapper.GamePlayersOpponentsMapper;
import com.xcf.mybatis.Service.GamePlayersOpponentsService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月5日 下午2:58:26 类说明
 */
@Service
public class GamePlayersOpponentsServiceimpl implements GamePlayersOpponentsService {
	@Autowired
	private GamePlayersOpponentsMapper gamePlayersOpponentsMapper;

	/**
	 * 验证是否存在
	 */
	@Override
	public GamePlayersOpponents checkexist(GamePlayersOpponents model) {
		GamePlayersOpponents search = new GamePlayersOpponents();
		if (model != null && model.getPlayerId() != null) {
			search.setPlayerId(model.getPlayerId());
		}
		if (model != null && model.getOpponentsType() != null && model.getOpponentsType()!="") {
			search.setOpponentsType(model.getOpponentsType());
		}
		return gamePlayersOpponentsMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(GamePlayersOpponents model) {
		try {
			if (model.getId() != null) {
				return gamePlayersOpponentsMapper.updateByPrimaryKeySelective(model);
			} else {
				return gamePlayersOpponentsMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println("玩家:" + model.getNickname() + "====" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}

}
