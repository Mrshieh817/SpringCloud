package com.igxe.Controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年7月22日 上午11:22:04 类说明
 */
// 在执行所有controller之前,先執行這個方法
@RestControllerAdvice
@Slf4j
public class errorController {
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Object exception(Throwable throwable) {
		log.error("系统异常", throwable);
		Map<String, Object> map = new HashMap<>();
		map.put("mesage", throwable.getMessage());
		map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return map;

	}

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Object badRequestException(IllegalArgumentException exception) {
		log.error("系统异常", exception);
		Map<String, Object> map = new HashMap<>();
		map.put("mesage", exception.getMessage());
		map.put("code", HttpStatus.BAD_REQUEST.value());
		return map;
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
			UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Object badRequestException(Exception exception) {
		log.error("系统异常", exception);
		Map<String, Object> map = new HashMap<>();
		map.put("mesage", exception.getMessage());
		map.put("code", HttpStatus.BAD_REQUEST.value());
		return map;
	}

	@ExceptionHandler(Exception.class)
	public Object Exception(Exception throwable) {
		log.error("系统异常", throwable);
		Map<String, Object> map = new HashMap<>();
		map.put("mesage", throwable.getMessage());
		map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return map;

	}

}
