package com.xcf.mybatis.connfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.xcf.mybatis.Annotation.EnhanceJacksonAnnotationIntrospector;

import cn.hutool.core.date.DatePattern;

/**
 * @author xcf
 * @Date 创建时间：2021年3月19日 下午12:16:45
 */
@Configuration
public class GlobalConfig {

    @Bean
    @Primary
    ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.createXmlMapper(false).build();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            // 序列化Date时 使用中文语言
            builder.locale(Locale.CHINA);
            // 序列化Date时 使用系统默认时区
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));

            // 序列化ENUM时调用.toString()方法
            builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            // 允许反序列化空对象
            builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            // 加载时间模组
            builder.modules(new JavaTimeModule());
            // 忽略transient字段
            builder.featuresToEnable(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
            // 反序列化Getter/Creator/isGetter
            builder.visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // 区分大小写
            builder.featuresToDisable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
            // 允许使用JsonProperty重命名
            builder.featuresToEnable(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING);
            builder.featuresToEnable(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING);
            // Date默认写成时间戳
            builder.featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // 使用自定义的注解内省器
            builder.annotationIntrospector(new EnhanceJacksonAnnotationIntrospector());
        };
    }


    @SuppressWarnings("serial")
	static class JavaTimeModule extends SimpleModule {

        public JavaTimeModule() {
            super(PackageVersion.VERSION);
            this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
            this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        }
    }
}
