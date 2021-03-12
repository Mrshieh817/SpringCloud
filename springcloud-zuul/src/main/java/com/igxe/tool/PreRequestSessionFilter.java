package com.igxe.tool;
/**
* @author 作者:大飞
* @version 创建时间：2020年5月7日 上午11:14:13
* 类说明
*/

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 经过zuul请求时, zuul进行session的转发
 */
 
//@Configuration
public class PreRequestSessionFilter extends ZuulFilter {
    private static final Logger log = LoggerFactory.getLogger(PreRequestSessionFilter.class);
 
    @Autowired
    HttpServletRequest httpServletRequest;
 
    @Override
    public String filterType() {
        return "pre";
    }
 
    /**
     * 返回一个值来指定过滤器的执行顺序，不同过滤器允许返回相同的数字，数字越小顺序越靠前
     */
    @Override
    public int filterOrder() {
        return 1;
    }
 
    /**
     * 返回一个boolean值来判断该过滤器是否要执行，true：执行，false：不执行
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }
 
    /**
     * 过滤器的具体逻辑
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String sessionId = httpServletRequest.getSession().getId();
        log.info("--------------------------------------------------");
        log.info("sessionId: " + sessionId);
        log.info("--------------------------------------------------");
        ctx.addZuulRequestHeader("Cloud-Cookie", "SESSION=" + sessionId);
        ctx.setSendZuulResponse(true);  // 对该请求进行路由
        ctx.setResponseStatusCode(200); // 返回200正确响应
 
        return null;
    }
}
