package com.xcf.mybatis.Core.Delay;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
* @author xcf 
* @Date 创建时间：2021年4月7日 下午3:11:54 
* 延时job
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DelayJob implements Serializable{
	private Map jobParams;//job执行参数
    private Class aClass;//具体执行实例实现
    private String nametest;
}
