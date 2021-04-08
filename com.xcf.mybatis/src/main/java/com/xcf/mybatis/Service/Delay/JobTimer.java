package com.xcf.mybatis.Service.Delay;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBoundedBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.xcf.mybatis.Core.Delay.DelayJob;

/** 
* @author xcf 
* @Date 创建时间：2021年4月7日 下午3:15:14 
*/
@Component
public class JobTimer {
	     static final String jobsTag = "customer_jobtimer_jobs";
	     
	     @Value("${spring.redis.host}")
	     private String host;

	     @Value("${spring.redis.port}")
	     private String port;

	    @Autowired
	    private RedissonClient client;

	    @Autowired
	    private ApplicationContext context;

	    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	    @PostConstruct
	    public void startJobTimer() {
	    	Config config = new Config();
			config.useSingleServer().setAddress("redis://"+host+":"+port+"");
			RedissonClient redisson = Redisson.create(config);
	    	RBlockingQueue  blockingQueue= redisson.getBlockingQueue(jobsTag);
	        new Thread() {
	            @Override
	            public void run() {
	                while (true) {
	                    try {
	                        DelayJob job =(DelayJob)blockingQueue.take();
	                        executorService.execute(new ExecutorTask(context, job));
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        try {
	                            TimeUnit.SECONDS.sleep(60);
	                        } catch (Exception ex) {
	                        }
	                    }
	                }
	            }
	        }.start();
	    }
	    class ExecutorTask implements Runnable {

	        private ApplicationContext context;

	        private DelayJob delayJob;

	        public ExecutorTask(ApplicationContext context, DelayJob delayJob) {
	            this.context = context;
	            this.delayJob = delayJob;
	        }

	        @Override
	        public void run() {
	            //ExecuteJob service = (ExecuteJob) context.getBean(delayJob.getClass());
	            //service.execute(delayJob);
	        	System.out.println("我是获取的："+delayJob.getNametest());
	        }
	    }
}
