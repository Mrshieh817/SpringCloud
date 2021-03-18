package com.xcf.mybatis.Service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.TeamBasic;
import com.xcf.mybatis.Mapper.TeambasicMapper;
import com.xcf.mybatis.Service.TeambasicService;


/**
* @author 作者:大飞
* @version 创建时间：2019年11月1日 下午2:41:46
* 类说明
*/

@Service
public class TeambasicServiceimpl implements TeambasicService {
	@Resource
	private TeambasicMapper teambasicMapper;
	
	/**
	 * 验证是否存在
	 */
	@Override
	public TeamBasic checkexsit(TeamBasic model){
		TeamBasic search=new TeamBasic();
		if (model!=null && model.getTeamId()!=null) {
			search.setTeamId(model.getTeamId());
		}	
	   return teambasicMapper.selectOne(search);

	}
	
	
	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer save(TeamBasic model){
		try {
			if (model.getId()!=null) {
				return teambasicMapper.updateByPrimaryKeySelective(model);
			}else{
				return teambasicMapper.insert(model);
			}						
		} catch (Exception e) {
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
			 return 0;
		}
	}
}
