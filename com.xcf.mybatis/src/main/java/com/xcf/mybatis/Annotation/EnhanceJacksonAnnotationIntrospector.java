package com.xcf.mybatis.Annotation;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/** 
* @author xcf 
* @Date 创建时间：2021年3月19日 上午10:53:38 
*/
public class EnhanceJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {
    @Override
    public Object findSerializer(Annotated a) {
        // 调用父类逻辑保证原注解继续生效
        Object serializer = super.findSerializer(a);
        // Jackson原装进口注解优先
        if (serializer == null) {
            // 只对Getter生效
            if (a instanceof AnnotatedMethod) {
                // 返回值类型为String
                if (a.getType().getRawClass().equals(String.class)) {
                    // 头上有MaskProperty注解
                    JsonRegularReplace jsonRegularReplace = a.getAnnotation(JsonRegularReplace.class);
                    if (null != jsonRegularReplace) {
                        // 实例化出自己的序列化器并返回
                        return new StringRegularReplaceSerializer(jsonRegularReplace);
                    }
                }
            }
        }
        return serializer;
    }
}
