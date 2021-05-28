package com.xcf.mybatis.Tool;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.redisson.api.RedissonClient;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

/**
 * @author xcf
 * @Date 创建时间：2021年5月28日 下午2:13:53
 */
@RequiredArgsConstructor
public class CorsFilter extends OncePerRequestFilter {
	
	//private final RedissonClient redissonClient; 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		System.out.println("*********************************过滤器被使用**************************");
		chain.doFilter(request, response);
	}
}
