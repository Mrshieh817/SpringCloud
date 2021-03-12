package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.TeamMatches;
import com.xcf.mybatis.Mapper.TeamMatchesMapper;
import com.xcf.mybatis.Service.TeamMatchesService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月1日 下午4:19:11 类说明
 */
@Service
public class TeamMatchesServiceimpl implements TeamMatchesService {
	@Autowired
	private TeamMatchesMapper teamMatchesMapper;

	/**
	 * 验证是否存在
	 */
	@Override
	public TeamMatches checkexist(TeamMatches model) {
		TeamMatches search = new TeamMatches();
		if (model != null && model.getTeamId() != null) {
			search.setTeamId(model.getTeamId());
		}
		return teamMatchesMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(TeamMatches model) {
		try {
			if (model.getId() != null) {
				return teamMatchesMapper.updateByPrimaryKeySelective(model);
			} else {
				return teamMatchesMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}
}
