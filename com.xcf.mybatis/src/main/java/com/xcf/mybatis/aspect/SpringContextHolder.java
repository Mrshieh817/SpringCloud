package com.xcf.mybatis.aspect;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/** 
* @author xcf 
* @Date 创建时间：2021年3月11日 下午1:58:47 
*/
@Slf4j
@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {


    private static ApplicationContext applicationContext = null;
    @Getter
    private static String applicationName = null;
    @Getter
    private static boolean isDebug = true;
    @Getter
    private static boolean isDev = true;
    private static Integer serverPort = null;
//
//    public static Boolean isDebug() {
//        return null == isDebug ? false : isDebug;
//    }

    public static Integer getServerPort() {
        return serverPort;
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
        SpringContextHolder.applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");

        SpringContextHolder.isDebug = Objects.equals(applicationContext.getEnvironment().getProperty("debug"), "true");

        SpringContextHolder.isDev = Stream.of(applicationContext.getEnvironment().getActiveProfiles())
            .anyMatch(s -> !s.equals("prod"));

        String serverPortStr = applicationContext.getEnvironment().getProperty("server.port");
        Optional.ofNullable(serverPortStr).ifPresent(s -> SpringContextHolder.serverPort = Integer.valueOf(s));


        log.warn("当前注册中心配置:{}",applicationContext.getEnvironment().getProperty("spring.cloud.nacos.server-addr"));
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        if (log.isDebugEnabled()) {
            log.debug("清理SpringContextHolder");
        }
        SpringContextHolder.applicationContext = null;
        SpringContextHolder.applicationName = null;
        SpringContextHolder.isDev = true;
        SpringContextHolder.isDebug = true;
        SpringContextHolder.serverPort = null;
    }

    /**
     * 发布事件
     *
     * @param event
     */
    public static void publishEvent(ApplicationEvent event) {
        if (applicationContext == null) {
            return;
        }
        applicationContext.publishEvent(event);
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() {
        SpringContextHolder.clearHolder();
    }

}