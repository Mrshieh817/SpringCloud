package com.xcf.mybatisplus.controller;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.xcf.mybatisplus.model.Vo.KResponse;

/**
 * @author xcf
 * @Date 创建时间：2021年4月25日 下午4:54:34
 */
@RestControllerAdvice
@SuppressWarnings("all")
@Order(50)
public class GlobalExceptionAdviceController {

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public KResponse<Object> handlerBindException(BindException e) {

		BindingResult bindingResult = e.getBindingResult();
		String message = handlerBindResult(bindingResult);
		return KResponse.failed().setStatusMessage(bindingResult.getFieldError().getDefaultMessage())
				.setCustomData(message);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public KResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		BindingResult bindingResult = e.getBindingResult();
		String message = handlerBindResult(bindingResult);
		return KResponse.failed().setStatusMessage(bindingResult.getFieldError().getDefaultMessage())
				.setCustomData(message);
	}

	private String handlerBindResult(BindingResult bindingResult) {
		// 拼装错误信息
		StringJoiner sj = new StringJoiner(",\n", "{\n", "\n}");
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError error : fieldErrors) {
			sj.add(error.getField() + ":" + error.getDefaultMessage());
		}
		return sj.toString();
	}
}
