package com.xcf.mybatisplus.model.Vo;

import lombok.Data;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午4:50:15 
*/
@Data
public class ExMessage {
    /**
     * statusMessage 状态信息
     * 用于后端debug
     */
    private String message;
    /**
     * statusCode 状态码
     * 用于前端i18n
     */
    private int code;

    public ExMessage(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public ExMessage(int code) {
        this.message = "操作失败,请根据状态码判断";
        this.code = code;
    }
}