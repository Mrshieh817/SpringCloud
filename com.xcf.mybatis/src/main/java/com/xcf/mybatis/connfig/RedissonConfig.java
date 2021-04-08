package com.xcf.mybatis.connfig;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 
* @author xcf 
* @Date 创建时间：2021年4月7日 下午3:56:32 
*/
@Configuration
public class RedissonConfig {
	
	@Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(name = "redisson")
    public RedissonClient redisson() throws IOException {
        // 本例子使用的是yaml格式的配置文件，读取使用Config.fromYAML，如果是Json文件，则使用Config.fromJSON
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-config.yml"));
        return Redisson.create(config);
		/*
		 * Config config = new Config(); SingleServerConfig singleServerConfig =
		 * config.useSingleServer(); singleServerConfig.setAddress("redis://" + host +
		 * ":" + port); if (StringUtils.isNotBlank(password)) {
		 * singleServerConfig.setPassword(password); } return Redisson.create(config);
		 */
    }
}
