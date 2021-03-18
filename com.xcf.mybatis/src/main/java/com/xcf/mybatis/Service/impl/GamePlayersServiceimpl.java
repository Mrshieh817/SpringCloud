package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayers;
import com.xcf.mybatis.Mapper.GamePlayersMapper;
import com.xcf.mybatis.Service.GamePlayersService;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 上午11:05:56
* 类说明
*/
@Service
public class GamePlayersServiceimpl implements GamePlayersService {
 @Autowired
 private GamePlayersMapper gamePlayersMapper;
 
 /**
	 * 验证是否存在
	 */
	@Override
	public GamePlayers checkexist(GamePlayers model){
		GamePlayers search=new GamePlayers();
		if (model!=null && model.getPlayerId()!=null) {
			search.setPlayerId(model.getPlayerId());
		}	
	   return gamePlayersMapper.selectOne(search);

	}
	
	
	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer save(GamePlayers model){
		try {
			if (model.getId()!=null) {
				return gamePlayersMapper.updateByPrimaryKeySelective(model);
			}else{
				return gamePlayersMapper.insert(model);
			}						
		} catch (Exception e) {
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
			 return 0;
		}
	}
}
