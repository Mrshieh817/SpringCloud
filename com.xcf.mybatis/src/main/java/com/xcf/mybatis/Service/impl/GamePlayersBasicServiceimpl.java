package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.GamePlayersBasic;
import com.xcf.mybatis.Mapper.GamePlayersBasicMapper;
import com.xcf.mybatis.Service.GamePlayersBasicService;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月4日 下午2:47:09
* 类说明
*/
@Service
public class GamePlayersBasicServiceimpl implements GamePlayersBasicService{

	@Autowired
	private GamePlayersBasicMapper gamePlayersBasicMapper;
	/**
	 * 验证是否存在
	 */
	@Override
	public GamePlayersBasic checkexist(GamePlayersBasic model){
		GamePlayersBasic search=new GamePlayersBasic();
		if (model!=null && model.getPlayerId()!=null) {
			search.setPlayerId(model.getPlayerId());
		}	
	   return gamePlayersBasicMapper.selectOne(search);

	}
	
	
	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer save(GamePlayersBasic model){
		try {
			if (model.getId()!=null) {
				return gamePlayersBasicMapper.updateByPrimaryKeySelective(model);
			}else{
				return gamePlayersBasicMapper.insert(model);
			}						
		} catch (Exception e) {
			 System.out.println("玩家:"+model.getNickname()+"===="+e.getMessage());
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
			 return 0;
		}
	}
}
