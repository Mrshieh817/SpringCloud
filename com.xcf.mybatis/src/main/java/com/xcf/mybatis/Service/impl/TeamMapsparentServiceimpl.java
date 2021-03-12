package com.xcf.mybatis.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xcf.mybatis.Core.TeamMapsparent;
import com.xcf.mybatis.Mapper.TeamMapsparentMapper;
import com.xcf.mybatis.Service.TeamMapsparentService;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年11月1日 下午5:12:01 类说明
 */

@Service
public class TeamMapsparentServiceimpl implements TeamMapsparentService {
	@Autowired
	private TeamMapsparentMapper teamMapsparentMapper;

	/**
	 * 验证是否存在
	 */
	@Override
	public TeamMapsparent checkexist(TeamMapsparent model) {
		TeamMapsparent search = new TeamMapsparent();
		if (model != null && model.getTeamId() != null) {
			search.setTeamId(model.getTeamId());
		}
		return teamMapsparentMapper.selectOne(search);

	}

	/**
	 * 存储
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer save(TeamMapsparent model) {
		try {
			if (model.getId() != null) {
				return teamMapsparentMapper.updateByPrimaryKeySelective(model);
			} else {
				return teamMapsparentMapper.insert(model);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 关键
			return 0;
		}
	}
}
