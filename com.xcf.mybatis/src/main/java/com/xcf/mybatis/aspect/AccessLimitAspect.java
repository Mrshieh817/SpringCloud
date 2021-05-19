package com.xcf.mybatis.aspect;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.xcf.mybatis.Annotation.SpringElExpressionUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @author xcf 
* @Date 创建时间：2021年5月19日 上午9:19:13 
* 自定义限流注解AOP
*/
@Aspect
@Component
@Slf4j
public class AccessLimitAspect {
	
	@Autowired
	RedisTemplate redisTemplate;
	
	
	public static String parseEL(String distinctEL, String[] parameterNames, Object[] parameterArgs) {
        String distinctKey = null;
        out:
        if (!StringUtils.isEmpty(distinctEL)) {
            // 参数列表长度不一致
            if (parameterNames.length != parameterArgs.length) {
                log.warn("参数列表组装失败,跳过EL解析:{pNames:{},pArgsLengths:{}}", parameterNames, parameterArgs.length);
                break out;
            }
            Map<String, Object> root = Maps.newHashMapWithExpectedSize(parameterNames.length);
            for (int i = 0; i < parameterArgs.length; i++) {
                root.put(parameterNames[i], parameterArgs[i]);
            }
            distinctKey = SpringElExpressionUtils.getString(root, distinctEL, () -> null);
        }
        return distinctKey;
    }
	
	
	@Pointcut(value = "@annotation(AccessLimit)",argNames = "AccessLimit")
	public void  AccessLimitPoint(AccessLimit accesslimit) {
		log.info("定义了一个AccessLimit限流切点"+accesslimit.toString());
	}

	@SuppressWarnings("unchecked")
	@Around(value = "AccessLimitPoint(AccessLimit)", argNames = "JoinPoint,AccessLimit")
	public Object doAroundAdvice(ProceedingJoinPoint JoinPoint, AccessLimit accesslimit){
		log.info(">>>>>>>>>>Around");
		log.info("环绕通知的目标方法名：" + JoinPoint.getSignature().getName());
		try {
			log.info(">>>>>>>>>AccessLimit参数限制次数{}>>>>多少时间内{}",accesslimit.limit(),accesslimit.sec());
			
			int limit = accesslimit.limit();
            int sec = accesslimit.sec();
            String disEl=accesslimit.disEl();
            MethodSignature signature = (MethodSignature) JoinPoint.getSignature();
            String[] parameterNames = signature.getParameterNames();
            Object[] parameterArgs = JoinPoint.getArgs();
            String distinctKey = parseEL(disEl, parameterNames, parameterArgs);
            String key =distinctKey;
            Integer maxLimit = null;
            if(redisTemplate.opsForValue().get(key)!=null){
                maxLimit =Integer.valueOf(redisTemplate.opsForValue().get(key).toString()) ;
            }

            if (maxLimit == null) {
                redisTemplate.opsForValue().set(key, "1", sec, TimeUnit.SECONDS);  //set时一定要加过期时间
            } else if (maxLimit < limit) {
                redisTemplate.opsForValue().set(key, (maxLimit + 1)+"", sec, TimeUnit.SECONDS);
            } else {
                System.out.println("请求太频繁");
                return "请求太频繁";
            }			
			
			Object obj = JoinPoint.proceed();
			return obj;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return null;
	}
}
