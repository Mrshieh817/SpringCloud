package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayersCareer;
import com.xcf.mybatis.Mapper.GamePlayersCareerMapper;
import com.xcf.mybatis.Service.GamePlayersCareerService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月5日 上午11:07:21 类说明
 */
@Service
public class GamePlayersCareerServiceimpl implements GamePlayersCareerService {
	@Autowired
	private GamePlayersCareerMapper gamePlayersCareerMapper;

	/**
	 * 验证是否存在
	 */
	@Override
	public GamePlayersCareer checkexist(GamePlayersCareer model) {
		GamePlayersCareer search = new GamePlayersCareer();
		if (model != null && model.getPlayerId() != null) {
			search.setPlayerId(model.getPlayerId());
		}
		return gamePlayersCareerMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(GamePlayersCareer model) {
		try {
			if (model.getId() != null) {
				return gamePlayersCareerMapper.updateByPrimaryKeySelective(model);
			} else {
				return gamePlayersCareerMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println("玩家:" + model.getNickname() + "====" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}

}
