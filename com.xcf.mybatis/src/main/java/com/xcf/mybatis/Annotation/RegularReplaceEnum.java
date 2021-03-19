package com.xcf.mybatis.Annotation;

import java.util.Objects;

/**
 * @author xcf
 * @Date 创建时间：2021年3月19日 上午10:47:30
 * 字段掩码类型枚举 根据level向下兼容
 */
public enum RegularReplaceEnum {

	TOURIST("tourist", "游客", 0), MEMBER("member", "注册用户", 1), VIP("vip", "个人VIP服务", 2), C_VIP("c_vip", "企业VIP服务", 2),
	INCREMENT("increment", "增值服务", 2), ALL("all", "全部", 99);

	private String code;

	private String codeMsg;

	private Integer level;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public void setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
	}

	RegularReplaceEnum(String code, String codeMsg, Integer level) {
		this.code = code;
		this.codeMsg = codeMsg;
		this.level = level;
	}

	public static String getByCode(String code) {
		RegularReplaceEnum[] alarmGrades = RegularReplaceEnum.values();
		for (int i = 0; i < alarmGrades.length; i++) {
			if (Objects.equals(code, alarmGrades[i].getCode())) {
				return alarmGrades[i].getCodeMsg();
			}
		}
		return null;
	}

	/**
	 * 掩码枚举比较
	 * 
	 * @param annEnum 枚举数组
	 * @param nowEnum 需要判断得包含枚举
	 **/
	public static boolean isContain(RegularReplaceEnum annEnum, RegularReplaceEnum nowEnum) {
		if (annEnum == null || nowEnum == null)
			return false;
		if (annEnum == nowEnum || annEnum == RegularReplaceEnum.ALL)
			return true;
		RegularReplaceEnum[] alarmGrades = RegularReplaceEnum.values();
		for (int i = 0; i < alarmGrades.length; i++) {
			if (alarmGrades[i].level < annEnum.level && alarmGrades[i] == nowEnum)
				return true;
		}
		return false;
	}

}