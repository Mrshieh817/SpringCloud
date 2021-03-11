package com.xcf.mybatis.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/** 
* @author xcf 
* @Date 创建时间：2021年3月11日 下午1:57:06 
*/
@Slf4j
@Aspect
@Component
public class SimpleControllerLogAspect {

    @Before(value = "within(com.kailinjt.*.controller.*)&&@annotation(apiOperation)")
    public void before(JoinPoint point, ApiOperation apiOperation) {

        // 跳过该切面
        if (skipAop(point)) return;

        // 绑定注解到RequestContext
        RequestContextHolder.currentRequestAttributes()
            .setAttribute("apiOperation", apiOperation, RequestAttributes.SCOPE_REQUEST);

        // 请求地址
        this.callUrlLog("收到请求:{}");
        if (SpringContextHolder.isDebug()) {
            this.requestBodyLog((MethodInvocationProceedingJoinPoint) point);
        }
        this.swaggerApiLog(point, apiOperation, "{} => 开始");
    }

    @AfterReturning(value = "@annotation(apiOperation)", returning = "returnObj", argNames = "apiOperation,returnObj")
    public void afterReturning(JoinPoint point, ApiOperation apiOperation, Object returnObj) {

        // 跳过该切面
        if (skipAop(point)) return;

        if (SpringContextHolder.isDebug()) {
            this.responseBodyLog(returnObj, "响应:{}");
        }
        this.swaggerApiLog(point, apiOperation, "{} => 结束");
    }

    private void requestBodyLog(MethodInvocationProceedingJoinPoint point) {
        JoinPoint.StaticPart staticPart = point.getStaticPart();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // 找出头上顶注解的参数
        Object requestBodyParam = null;
        List<Object> requestParamList = new ArrayList<>();
        List<String> requestParamNamesList = new ArrayList<>();
        Object[] params = point.getArgs();
        String[] paramNames = signature.getParameterNames();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof RequestBody) {
                    requestBodyParam = params[i];
                }
                if (annotation instanceof RequestParam) {
                    requestParamList.add(params[i]);
                    requestParamNamesList.add(paramNames[i]);
                }
            }
        }
        if (Objects.nonNull(requestBodyParam)) {

            //TODO 判断 requestBodyParam 长度或大小 太长就不打印
            log.debug("requestBody:{}", requestBodyParam);
        }
        if (!CollectionUtils.isEmpty(requestParamList)) {
            StringJoiner sj = new StringJoiner(",", "{", "}");
            for (int i = 0; i < requestParamList.size(); i++) {
                sj.add(paramNames[i] + params[i]);
            }
            log.debug("requestParam:{}", sj.toString());
        }

    }

    /**
     * 跳过该日志切面
     *
     * @param point 切入点
     * @return 是否跳过
     */
    private boolean skipAop(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        for (Annotation declaredAnnotation : method.getDeclaredAnnotations()) {
            if (declaredAnnotation instanceof WebLog) {
                return true;
            }
        }
        return false;
    }


    private void responseBodyLog(Object returnObj, String format) {

        //TODO 判断 returnObj 长度或大小 太长就不打印
        log.debug(format, returnObj);
    }


    /**
     * <h3>Url请求日志</h3>
     */
    private void callUrlLog(String format) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            log.info("收到来自内部的调用");
        } else {
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            String requestURI = request.getRequestURI();
            log.info(format, requestURI);
        }
    }

    /**
     * <h3>swagger日志方法</h3>
     *
     * @param point        切入点
     * @param apiOperation ApiOperation注解对象
     * @param format       ApiOperation.value所使用的的日志模板
     */
    private void swaggerApiLog(JoinPoint point, ApiOperation apiOperation, String format) {
        // 注解值
        String apiOperationValue = apiOperation.value();
        // 运行时代理对象
        Object target = point.getTarget();
        // 获取被代理类的真实类型
        Class realClass = ((MethodInvocationProceedingJoinPoint) point).getStaticPart().getSourceLocation().getWithinType();

        // 出入口日志
        try {
            Field logField = realClass.getDeclaredField("log");
            logField.setAccessible(true);
            Object o = logField.get(target);
            Logger logger = (Logger) o;
            logger.info(format, apiOperationValue);
        } catch (NoSuchFieldException e) {
            // 没有找到Logger就会收到这个异常 此时调用此类的logger
            log.info(format, apiOperationValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}