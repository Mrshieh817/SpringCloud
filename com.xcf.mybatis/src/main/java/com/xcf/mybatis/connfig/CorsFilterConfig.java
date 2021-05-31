package com.xcf.mybatis.connfig;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xcf.mybatis.Tool.CorsFilter;

import lombok.var;

/** 
* @author xcf 
* @Date 创建时间：2021年5月28日 下午2:37:32 
*/
@Configuration
public class CorsFilterConfig {
	
	    //@Qualifier("RedissonClient")
	    @Autowired
	    RedissonClient redissonClient;
	    
	    @Bean
	    public FilterRegistrationBean<CorsFilter>jwtTokenFilter(){
	    	//var jwtTokenFilter = new CorsFilter(globalRedisClient);
	    	var jwtTokenFilter = new CorsFilter();
	        var bean = new FilterRegistrationBean<>(jwtTokenFilter);
	        bean.setOrder(OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER - 101);
	        return bean;
	    }
	    
}
