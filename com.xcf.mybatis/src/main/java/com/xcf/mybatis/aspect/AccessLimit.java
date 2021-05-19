package com.xcf.mybatis.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @author xcf 
* @Date 创建时间：2021年5月19日 上午9:16:52 
* 自定义限流注解
*/
@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {


    int limit() default 5; //限制的次数


    int sec() default 5;//限制的时间段（秒）
    
    String disEl() default "";//需要提取的el信息
}