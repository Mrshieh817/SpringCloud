package com.xcf.mybatis.Core;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月30日 上午11:29:15
* 类说明
*/
@Data
public class BaseModel {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 6180869216498363919L;

    @Column(name="id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;
}
