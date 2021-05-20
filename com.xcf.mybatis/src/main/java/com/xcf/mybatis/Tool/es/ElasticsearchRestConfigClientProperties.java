package com.xcf.mybatis.Tool.es;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/** 
* @author xcf 
* @Date 创建时间：2021年5月20日 下午2:23:45 
*/
@Data
@Component
@ConfigurationProperties(prefix = "spring.elasticsearch.restconfig")
public class ElasticsearchRestConfigClientProperties {
    private List<String> uris;
    private String username;
    private String password;
    private boolean ssl = false;
    private Duration connectionTimeout = Duration.ofSeconds(1L);
    private Duration readTimeout = Duration.ofSeconds(30L);
}
