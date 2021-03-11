package com.xcf.mybatis.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xcf
 * @Date 创建时间：2021年3月11日 下午1:43:31
 */
@Aspect
@Component
@Slf4j
//@Profile({ "dev" })
public class WebLogAspect {
	/**
	 * 以自定义 @WebLog 注解为切点
	 */
	@Pointcut(value = "@annotation(webLog)", argNames = "webLog")
	public void webLogPoint(WebLog webLog) {

	}

	@Around(value = "webLogPoint(webLog)", argNames = "JoinPoint,webLog")
	public Object doAroundAdvice(ProceedingJoinPoint JoinPoint, WebLog webLog) {
		log.info(">>>>>>>>>>Around");
		log.info("环绕通知的目标方法名：" + JoinPoint.getSignature().getName());
		try {
			log.info(">>>>>>>>>webLog参数{}，{}，{}",webLog.params(),webLog.value(),webLog.description());
			Object obj = JoinPoint.proceed();
			return obj;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return null;

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*   
	*//**
		 * 以自定义 @WebLog 注解为切点
		 */
	/*
	 * @Pointcut(value = "@annotation(webLog)", argNames = "webLog") public void
	 * webLogPoint(WebLog webLog) { }
	 * 
	 * @Around(value = "webLogPoint(webLog)", argNames = "joinPoint,webLog") public
	 * Object doAround(ProceedingJoinPoint joinPoint, WebLog webLog) throws
	 * Throwable {
	 * 
	 * // 开始打印请求日志 ApiOperation apiOperation = null; RequestMapping requestMapping =
	 * null; try { String methodDescription; ServletRequestAttributes attributes =
	 * (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	 * Method currentMethod = ((MethodSignature)
	 * joinPoint.getSignature()).getMethod(); Annotation[] methodAnnotations =
	 * currentMethod.getAnnotations(); // 查找注解 for (Annotation annotation :
	 * methodAnnotations) { if (annotation instanceof ApiOperation) { apiOperation =
	 * (ApiOperation) annotation; } requestMapping =
	 * AnnotatedElementUtils.findMergedAnnotation(currentMethod,
	 * RequestMapping.class); }
	 * 
	 * // 准备打印参数 String logArgStr = this.getLogArgStr(webLog, joinPoint);
	 * 
	 * // 判断是否为Controller层的方法 if (Objects.nonNull(requestMapping)) {
	 * HttpServletRequest request = attributes.getRequest(); // 填充操作信息 if
	 * (Objects.nonNull(apiOperation)) { // 绑定到RequestContextHolder中
	 * attributes.setAttribute("apiOperation", apiOperation,
	 * RequestAttributes.SCOPE_REQUEST); methodDescription = apiOperation.value(); }
	 * else { methodDescription = this.getAspectLogDescription(currentMethod,
	 * webLog); }
	 * 
	 * this.printRequestBeforeLog(joinPoint, request, methodDescription, logArgStr,
	 * true); } else { methodDescription =
	 * this.getAspectLogDescription(currentMethod, webLog);
	 * this.printMethodBeforeLog(joinPoint, methodDescription, logArgStr, true); } }
	 * catch (Exception e) { log.warn("@Weblog打印日志失败"); }
	 * 
	 * long startTime = System.currentTimeMillis(); Object result =
	 * joinPoint.proceed(); long endTime = System.currentTimeMillis(); long
	 * consuming = endTime - startTime;
	 * 
	 * try { String resultStr = objectMapper.writeValueAsString(result); if
	 * (Objects.nonNull(requestMapping)) { this.printRequestAfterLog(consuming,
	 * resultStr, true); } else { this.printMethodAfterLog(consuming, resultStr,
	 * true); } } catch (Exception e) { log.warn("@Weblog打印日志失败"); } return result;
	 * }
	 * 
	 * 
	 * @SneakyThrows private String getLogArgStr(WebLog webLog, JoinPoint joinPoint)
	 * {
	 * 
	 * String[] params = webLog.params(); Object printArg; if (params.length > 0) {
	 * printArg = this.scanParamsArg(joinPoint, params); } else { printArg =
	 * this.scanRequestBodyArg(joinPoint); }
	 * 
	 * return (null != printArg) ? objectMapper.writeValueAsString(printArg) : null;
	 * }
	 * 
	 *//**
		 * <h3>扫描切点上与paramNames匹配的参数</h3>
		 *
		 * @param joinPoint  切点
		 * @param paramNames 参数名称
		 * @return arg/null
		 */
	/*
	 * private Object scanParamsArg(JoinPoint joinPoint, String[] paramNames) {
	 * //TODO 找到参数就是用参数 return null; }
	 * 
	 *//**
		 * <h3>扫描切点上带RequestBody注解的参数</h3>
		 *
		 * @param joinPoint 切点
		 * @return arg/null
		 */
	/*
	 * @Nullable private Object scanRequestBodyArg(JoinPoint joinPoint) { Object[]
	 * args = joinPoint.getArgs(); Method currentMethod = ((MethodSignature)
	 * joinPoint.getSignature()).getMethod(); Annotation[][] parameterAnnotations =
	 * currentMethod.getParameterAnnotations(); int argNum; for (argNum = 0; argNum
	 * < parameterAnnotations.length; argNum++) { for (Annotation a :
	 * parameterAnnotations[argNum]) { if (a instanceof RequestBody) { return
	 * args[argNum]; } } } return null; }
	 * 
	 * private void printRequestBeforeLog(JoinPoint joinPoint, HttpServletRequest
	 * request, String methodDescription, String requestArgsStr, boolean printArg) {
	 * 
	 * // 打印请求相关参数 log.
	 * info("========================================== START =========================================="
	 * ); // 打印请求 url log.info("[{}] URL : {}", request.getMethod(),
	 * request.getRequestURL().toString()); // 打印描述信息 if
	 * (StringUtils.isNotBlank(methodDescription)) { log.info("Description: {}",
	 * methodDescription); } // 打印 Http method // log.info("HTTPMethod  : {}",
	 * request.getMethod()); // 打印调用 controller 的全路径以及执行方法
	 * log.info("ClassMethod: {}#{}",
	 * joinPoint.getSignature().getDeclaringTypeName(),
	 * joinPoint.getSignature().getName()); // 打印请求的 IP log.info("IP         : {}",
	 * request.getRemoteAddr()); // 打印请求入参 if (printArg &&
	 * StringUtils.isNotBlank(requestArgsStr)) { log.info("RequestArgs: {}",
	 * requestArgsStr); } }
	 * 
	 * private void printRequestAfterLog(long consuming, String resultStr, boolean
	 * printArg) {
	 * 
	 * log.info("Time-Consuming : {} ms", consuming);
	 * 
	 * if (printArg && StringUtils.isNotBlank(resultStr)) {
	 * log.info("Response Args  : {}", resultStr); }
	 * 
	 * log.
	 * info("=========================================== END ==========================================="
	 * ); }
	 * 
	 * 
	 * private void printMethodBeforeLog(JoinPoint joinPoint, String
	 * methodDescription, String logArgStr, boolean printArg) {
	 * log.info("======= METHOD ======="); if
	 * (StringUtils.isNotBlank(methodDescription)) { log.info("Operation:{}",
	 * methodDescription); } log.info("ClassMethod: {}#{}",
	 * joinPoint.getSignature().getDeclaringTypeName(),
	 * joinPoint.getSignature().getName()); if (printArg &&
	 * StringUtils.isNotBlank(logArgStr)) { log.info("MethodArg: {}", logArgStr); }
	 * }
	 * 
	 * private void printMethodAfterLog(long consuming, String resultStr, boolean
	 * printArg) { log.info("Consuming: {} ms", consuming); if (printArg) {
	 * log.info("ReturnArg: {}", resultStr); } log.info("========= END =========");
	 * }
	 * 
	 *//**
		 * 获取切面注解的描述
		 *
		 * @param joinPoint 切点
		 * @return 描述信息
		 * @throws Exception
		 *//*
			 * @SneakyThrows private String getAspectLogDescription(JoinPoint joinPoint) {
			 * String targetName = joinPoint.getTarget().getClass().getName(); String
			 * methodName = joinPoint.getSignature().getName(); Object[] arguments =
			 * joinPoint.getArgs(); Class targetClass = Class.forName(targetName); Method[]
			 * methods = targetClass.getMethods(); StringBuilder description = new
			 * StringBuilder(""); for (Method method : methods) { if
			 * (method.getName().equals(methodName)) { Class[] clazzs =
			 * method.getParameterTypes(); if (clazzs.length == arguments.length) {
			 * description.append(method.getAnnotation(WebLog.class).description()); break;
			 * } } } return description.toString(); }
			 * 
			 * @SneakyThrows private String getAspectLogDescription(Method currentMethod,
			 * WebLog webLog) {
			 * 
			 * return webLog.description(); }
			 */

}
