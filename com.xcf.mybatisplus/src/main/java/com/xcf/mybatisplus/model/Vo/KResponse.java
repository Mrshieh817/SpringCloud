package com.xcf.mybatisplus.model.Vo;

import java.io.Serializable;
import java.util.Optional;

import cn.hutool.core.text.StrFormatter;
import lombok.Data;
import lombok.experimental.Accessors;

/** 
* @author xcf 
* @Date 创建时间：2021年4月25日 下午4:47:53 
*/
@Data
@Accessors(chain = true)
public class KResponse<DATA> implements Serializable {

    private static final String FORMAT_STR = "00000";

    private static final long serialVersionUID = -1L;

    //@ApiModelProperty("请求状态")
    private Boolean status;

   // @ApiModelProperty(value = "消息说明数字码")
    private String statusCode;

    //@ApiModelProperty("信息[如果有，没有则不返回]")
    private String statusMessage;

    //@ApiModelProperty("返回数据")
    private DATA data;

   // @JsonInclude(value = JsonInclude.Include.NON_NULL)
    //@ApiModelProperty(value = "自定义附加响应数据体", hidden = true)
    private Object customData;

   // @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
   // @ApiModelProperty(value = "异常堆栈信息 仅在dev环境返回", hidden = true)
    private StackTraceElement[] errorStackTrace;

    public static <D> KResponse<D> success() {
        KResponse<D> response = new KResponse<>();
        response.setStatus(true);
        response.setStatusCode(0);
        return response;
    }

    public static <D> KResponse<D> success(String message) {
        KResponse<D> success = KResponse.<D>success();
        if (message != null) {
            success.setStatusMessage(message);
        }
        return success;
    }

    public static <D> KResponse<D> data(D data) {
        KResponse<D> success = KResponse.<D>success();
        success.setData(data);
        return success;
    }

    public static <D> KResponse<D> failed() {
        KResponse<D> failed = new KResponse<>();
        failed.setStatus(false);
        failed.setStatusCode(1);
        return failed;
    }

    public static <D> KResponse<D> failed(String statusMessage) {
        KResponse<D> failed = KResponse.<D>failed();
        Optional.ofNullable(statusMessage).ifPresent(s -> failed.setStatusMessage(statusMessage));
        return failed;
    }

    public static <D> KResponse<D> failed(ExMessage exMessage) {
        KResponse<D> failed = KResponse.<D>failed();
        Optional.ofNullable(exMessage).ifPresent(ex -> failed.setStatusMessage(exMessage.getMessage()).setStatusCode(exMessage.getCode()));
        return failed;
    }

    public static <D> KResponse<D> failed(Integer statusCode, String statusMessage) {
        KResponse<D> failed = KResponse.<D>failed(statusMessage);
        Optional.ofNullable(statusCode).ifPresent(s -> failed.setStatusCode(statusCode));
        return failed;
    }

    public static <D> KResponse<D> failed(String statusCodeStr, String statusMessage) {
        KResponse<D> failed = KResponse.<D>failed(statusMessage);
        failed.statusCode = statusCodeStr;
        failed.statusMessage = statusMessage;
        return failed;
    }

    public KResponse<DATA> setStatusCode(Integer statusCode) {
        this.statusCode = calcStatusCode(statusCode);
        return this;
    }

    private String calcStatusCode(Integer statusCode) {
        if (statusCode == null || statusCode < 0) {
            this.setStatusMessage(StrFormatter.format("statusCode错误(statusCode:{})", statusCode));
            return "00001";
        } else if (statusCode > 9999) {
            return String.valueOf(statusCode);
        } else {
            String codeStr = String.valueOf(statusCode);
            return FORMAT_STR.substring(0, FORMAT_STR.length() - codeStr.length()) + codeStr;
        }
    }

    public void setStatusCode(String prefix, Integer statusCode) {

        if (prefix != null && statusCode < 10000) {
            this.statusCode = prefix + "-" + calcStatusCode(statusCode);
        } else {
            this.statusCode = calcStatusCode(statusCode);
        }
    }

	/*
	 * public void setErrorStackTrace(StackTraceElement[] errorStackTrace) { if
	 * (SpringContextHolder.isDebug()) { int stackHeight =
	 * Math.min(errorStackTrace.length, 10); this.errorStackTrace =
	 * Arrays.copyOf(errorStackTrace, stackHeight); } }
	 */
}