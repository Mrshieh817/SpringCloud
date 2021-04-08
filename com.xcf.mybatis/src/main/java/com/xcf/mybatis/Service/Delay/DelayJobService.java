package com.xcf.mybatis.Service.Delay;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xcf.mybatis.Core.Delay.DelayJob;

/**
 * @author xcf
 * @Date 创建时间：2021年4月7日 下午3:32:47 延时job服务
 */
@Component
public class DelayJobService {
	@Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

	public void submitJob(DelayJob job, long delay, TimeUnit timeUnit) {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://"+host+":"+port+"");
		RedissonClient redisson = Redisson.create(config);
		
		RBoundedBlockingQueue blockingQueue = redisson.getBoundedBlockingQueue(JobTimer.jobsTag);
		RDelayedQueue delayedQueue = redisson.getDelayedQueue(blockingQueue);
		delayedQueue.offer(job, delay, timeUnit);
		// 使用destroy销毁,会导致消息不及时
		delayedQueue.destroy();
		System.out.println("请求了");
	}

}
