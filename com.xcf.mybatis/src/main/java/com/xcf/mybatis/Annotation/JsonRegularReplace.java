package com.xcf.mybatis.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @author xcf 
* @Date 创建时间：2021年3月19日 上午9:36:20 
* 用于替换字符串
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface JsonRegularReplace {

    /**
     * 是否触发正则替换的EL表达式
     * 当与权限注解混合使用时将不会生效
     */
    String canReplaceEl() default "T(Boolean).FALSE";
    
    /**
     * 该属性与权限注解绑定后生效
     * @return
     */
    RegularReplaceEnum contain() default RegularReplaceEnum.TOURIST;

    /**

    /**
     * 正则表达式-分组
     * String.replaceAll( groupRegular(), replacement())
     */
    String groupRegular() default "";

    /**
     * 正则表达式-替换
     * String.replaceAll( groupRegular(), replacement())
     */
    String replacement() default "";
}
