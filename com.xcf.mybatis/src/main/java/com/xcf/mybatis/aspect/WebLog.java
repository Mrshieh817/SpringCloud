package com.xcf.mybatis.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/** 
* @author xcf 
* @Date 创建时间：2021年3月11日 下午1:50:22 
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {

    /**
     * 日志描述信息
     *
     * @return
     */
    //@AliasFor("value")
    String description() default "";

    //@AliasFor("description")
    String value() default "";

    /**
     * <h3>需要打印的参数</h3>
     * <p></p>
     */
    String[] params() default {};
}
