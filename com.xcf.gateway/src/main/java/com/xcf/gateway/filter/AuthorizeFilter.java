package com.xcf.gateway.filter;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/** 
* @author xcf 
* @Date 创建时间：2021年6月10日 上午9:40:52 
* 全局拦截
*/
//@Component
@Slf4j
@AllArgsConstructor
public class AuthorizeFilter implements GlobalFilter, Ordered {

    public static final String API_URI = "/v2/api-docs";

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	System.out.println();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getURI().getPath();
/*        if (url.indexOf("/login") >= 0){
            return chain.filter(exchange);
        }*/
        
       if (url.indexOf(API_URI) >= 0){  //  <------------------------------
            return chain.filter(exchange);
        }
        //后续业务逻辑代码....
       return this.response401(exchange);
     }
    
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
    
    /**
     * 返回401+异常信息
     *
     * @param exchange
     * @return
     */
    private Mono<Void> response401(ServerWebExchange exchange) {

        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
   
}
