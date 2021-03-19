package com.xcf.mybatis.Annotation;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/** 
* @author xcf 
* @Date 创建时间：2021年3月19日 上午10:50:07 
*/
@Slf4j
public class SpringElExpressionUtils {

    /**
     * 解析布尔值
     *
     * @param root         解析的根对象
     * @param elExpression 解析的表达式
     * @param fallback     解析失败降级函数
     * @return boolean
     */
    public static <T> boolean getBoolean(T root, String elExpression, Supplier<Boolean> fallback) {

        AtomicReference<Boolean> booleanV = new AtomicReference<>(false);
        try {
            var context = new StandardEvaluationContext(root);
            var parser = new SpelExpressionParser();
            var expression = parser.parseRaw(elExpression);
            Boolean aBoolean = expression.getValue(context, Boolean.class);
            aBoolean = Optional.ofNullable(aBoolean).orElseGet(fallback);
            booleanV.set(aBoolean);
        } catch (Exception e) {
            log.warn("计算EL表达式失败", e);
            Optional.ofNullable(fallback).ifPresent(booleanSupplier -> {
                booleanV.set(fallback.get());
            });
        }
        return booleanV.get();
    }


    /**
     * 解析字符串
     *
     * @param root         解析的根对象
     * @param elExpression 解析的表达式
     * @param fallback     解析失败降级函数
     * @return String
     */
    public static <T> String getString(T root, String elExpression, Supplier<String> fallback) {

        AtomicReference<String> strV = new AtomicReference<>();
        try {
            var context = new StandardEvaluationContext(root);
            var parser = new SpelExpressionParser();
            var expression = parser.parseExpression(elExpression);
            String str = expression.getValue(context, String.class);
            str = Optional.ofNullable(str).orElseGet(fallback);
            strV.set(str);
        } catch (Exception e) {
            log.warn("计算EL表达式失败", e);
            Optional.ofNullable(fallback).ifPresent(booleanSupplier -> {
                strV.set(fallback.get());
            });
        }
        return strV.get();
    }
}
