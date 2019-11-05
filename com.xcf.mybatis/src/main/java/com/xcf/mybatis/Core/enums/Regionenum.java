package com.xcf.mybatis.Core.enums;

import lombok.Getter;

/**
* @author 作者:大飞
* @version 创建时间：2019年10月29日 下午3:52:57
* 类说明
*/
@Getter
public enum Regionenum {
	     noting(0,"Nothing"),
	     euwest(1,"EU west"),
	     eueast(2,"EU east"),
	     asiaoceania(3,"Asia & Oceania"),
	     america(4,"America"),
	    ;

	    private Integer code;

	    private String message;

	    Regionenum(Integer code, String message) {
	        this.code = code;
	        this.message = message;
	    }
	    
	    /**
	     * 根据code获取message
	     * @Title getMessage
	     * @Description  
	     * @param code
	     * @return 
	     * @since 2019年7月10日 下午3:54:16
	     */
	    public static String getMessage(Integer code){
	    	for(Regionenum status: Regionenum.values()){
				if(code==status.getCode()){
					return status.getMessage();
				}
			}
	    	return code+"";
	    }
	    /**
	     * 根据message获取code
	     * @Title getMessage
	     * @Description  
	     * @param code
	     * @return 
	     * @since 2019年7月10日 下午3:54:16
	     */
	    public static Integer getCode(String message){
	    	for(Regionenum status: Regionenum.values()){
				if(status.name().equals(message.trim())){
					return status.getCode();
				}
			}
	    	return 0;
	    }
}
