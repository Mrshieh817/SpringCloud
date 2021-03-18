package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayersMatches;
import com.xcf.mybatis.Mapper.GamePlayersMatchesMapper;
import com.xcf.mybatis.Service.GamePlayersMatchesService;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午5:48:44
* 类说明
*/

@Service
public class GamePlayersMatchesServiceimpl implements GamePlayersMatchesService{

	@Autowired
	private GamePlayersMatchesMapper gamePlayersMatchesMapper;
	
	/**
	 * 验证是否存在
	 */
	@Override
	public GamePlayersMatches checkexist(GamePlayersMatches model){
		GamePlayersMatches search=new GamePlayersMatches();
		if (model!=null && model.getPlayerId()!=null) {
			search.setPlayerId(model.getPlayerId());
		}	
	   return gamePlayersMatchesMapper.selectOne(search);

	}
	
	
	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer save(GamePlayersMatches model){
		try {
			if (model.getId()!=null) {
				return gamePlayersMatchesMapper.updateByPrimaryKeySelective(model);
			}else{
				return gamePlayersMatchesMapper.insert(model);
			}						
		} catch (Exception e) {
			 System.out.println("玩家:"+model.getNickname()+"===="+e.getMessage());
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
			 return 0;
		}
	}
}
