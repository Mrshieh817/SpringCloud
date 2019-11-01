package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.TeamMapschild;
import com.xcf.mybatis.Mapper.TeamMapschildMapper;
import com.xcf.mybatis.Service.TeamMapschildService;

/**
* @author 作者:大飞
* @version 创建时间：2019年11月1日 下午5:38:16
* 类说明
*/
@Service
public class TeamMapschildServiceimpl implements TeamMapschildService {
 @Autowired
 private TeamMapschildMapper teamMapschildMapper;
 
 /**
	 * 验证是否存在
	 */
	@Override
	public TeamMapschild checkexist(TeamMapschild model) {
		TeamMapschild search = new TeamMapschild();
		if (model != null && model.getTeamId() != null && model.getMapstypeId()!=null) {
			search.setTeamId(model.getTeamId());
			search.setMapstypeId(model.getMapstypeId());
		}
		return teamMapschildMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(TeamMapschild model) {
		try {
			if (model.getId() != null) {
				return teamMapschildMapper.updateByPrimaryKeySelective(model);
			} else {
				return teamMapschildMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}
}
