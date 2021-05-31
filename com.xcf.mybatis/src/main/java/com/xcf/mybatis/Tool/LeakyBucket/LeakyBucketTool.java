package com.xcf.mybatis.Tool.LeakyBucket;

import org.redisson.api.RedissonClient;

/**
 * @author xcf
 * @Date 创建时间：2021年5月31日 上午11:09:11 漏桶算法
 */
//@Component
public class LeakyBucketTool {
	
	private RedissonClient redissonClient;
	/**
	 * 水流出的速度(1s 一条)
	 */
	private double rate;

	/**
	 * 桶的大小
	 */
	private double burst;

	/**
	 * 最后更新时间
	 */
	private long refreshTime;

	/**
	 * 桶里面剩余的水量
	 */
	private int water;
	
	/**
	 * 桶key,用于分布式
	 */
	private String leackykey;

	/**
	 * 初始化一些信息
	 * 
	 * @param rate
	 * @param burst
	 */
	public LeakyBucketTool(double rate, double burst) {
		this.rate = rate;
		this.burst = burst;
	}

	/**
	 * 刷新桶里面剩余的水量
	 */
	private void refreshWater() {
		long now = System.currentTimeMillis();
		water = (int) Math.max(0, water - (now - refreshTime) * rate);
		refreshTime = now;
	}
	
	/**
	 * 获取漏桶令牌，是否有资格获取当前的令牌
	 * @return
	 */
	public synchronized boolean trybucket() {
		refreshWater();
		if (water<burst) {
			water++;
			return true;
		}else {
			return false;
		}
	}
}
