package com.xcf.mybatis.Service.Delay;

import com.xcf.mybatis.Core.Delay.DelayJob;

/** 
* @author xcf 
* @Date 创建时间：2021年4月7日 下午3:14:32 
* 延时job执行器接口
*/
public interface ExecuteJob {
	void execute(DelayJob job);
}
