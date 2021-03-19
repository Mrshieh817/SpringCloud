package com.xcf.mybatis.Annotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

/** 
* @author xcf 
* @Date 创建时间：2021年3月19日 上午10:56:15 
*/
public class JsonRegularReplaceTest {

    public void test() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(new EnhanceJacksonAnnotationIntrospector());
        A a = new A();
        a.setAge(10);
        a.setName("ASDASDASD");
        a.setMobile("17623551311");
        a.setMobile2("17623551311");
        a.setMobile3("17623551311");
        System.out.println(objectMapper.writeValueAsString(a));

        /**
         * 字符串掩码表达式
         * (.{0,10})(.{10,})
         * 第一位保存  第二位截取替换
         * $1***************
         **/
        String address = "重庆市九龙坡区石桥铺街道大西洋国际35楼10-1";
        Pattern addPattern = Pattern.compile("(.{0,10})(.{10,})");
        Matcher addMatcher = addPattern.matcher(address);
        String addStr = addMatcher.replaceAll("$1***************");
        System.out.println(addStr);

        /**
         * 邮箱掩码表达式
         * (.?)(.+)(.)(@)
         * 第一位保存 第二位截取替换 第三位第四位保存
         * $1****$3$4
         **/
        String email = "1a8p@qq.com";
        Pattern emailPattern = Pattern.compile("(.?)(.+)(.)(@)");
        Matcher emailMatcher = emailPattern.matcher(email);
        String emailStr = emailMatcher.replaceAll("$1****$3$4");
        System.out.println(emailStr);
    }

    @Data
    static class A {

        private String name;

        private int age;

        private String mobile;

        @JsonRegularReplace(canReplaceEl = "T(Boolean).FALSE",groupRegular = "(\\d{3})(\\d{4})(\\d{4})",replacement = "$1****$2")
        private String mobile2;

        // 17623551311被分割为 176 2355 1311 三部分
        // $1****$2  表示取`第1部分`与'****'与`第2部分`拼接
        // 得到结果 176****2355
        @JsonRegularReplace(groupRegular = "(\\d{3})(\\d{4})(\\d{4})",replacement = "$1****$2")
        private String mobile3;

        // 17623551311被分割为 176 2355 1311 三部分
        // $1****$2  表示取`第1部分`与'****'与`第3部分`拼接
        // 得到结果 176****1311
        @JsonRegularReplace(groupRegular = "(\\d{3})(\\d{4})(\\d{4})",replacement = "$1****$3")
        public String getMobile4() {
            return mobile3;
        }
    }
}
