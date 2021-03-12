package com.xcf.mybatis.Tool.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author 作者:大飞
 * @version 创建时间：2018年2月10日 上午9:17:59 类说明
 */
@Component
public class RedisUtil {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	@Autowired
	private RedisModel RedisModel;
	private Jedis jedis;// 非切片额客户端连接
	private JedisPool jedisPool;// 非切片连接池
	private ShardedJedis shardedJedis;// 切片额客户端连接
	private ShardedJedisPool shardedJedisPool;// 切片连接池
	private  String PASSWORD = "";
	private String ip = "127.0.0.1";
	private int port = 6397;

	public void getconnection() {

		this.PASSWORD=RedisModel.getPassword();
		this.port=RedisModel.getPort();
		this.ip=RedisModel.getHost();
		initialPool();
		initialShardedPool();
		shardedJedis = shardedJedisPool.getResource();
		jedis = jedisPool.getResource();

	}

	/**
	 * 初始化非切片池
	 */
	private void initialPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setTestOnBorrow(false);
		config.setTestWhileIdle(true);
		config.setMinEvictableIdleTimeMillis(60000L);
		config.setNumTestsPerEvictionRun(1);
		config.setTimeBetweenEvictionRunsMillis(30000L);

		if (!PASSWORD.equals("")) {
			jedisPool = new JedisPool(config, ip, port, 10000, PASSWORD);
		} else {
			jedisPool = new JedisPool(config, ip, port);
		}
	}

	private static RedisUtil Singleton = new RedisUtil();

	public static RedisUtil getSingleton() {
		return Singleton;
	}

	private Jedis getJedis() {
		if (jedisPool == null)
			initialPool();
		return jedisPool.getResource();
	}

	@SuppressWarnings("deprecation")
	private void returnJedis(Jedis jedis) {
		if (jedis != null)
			jedisPool.returnResource(jedis);
	}

	/**
	 * 添加数据
	 * 
	 * @param key
	 * @return
	 */
	public void setString(String key, String value, int... times) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.set(key, value);
			if (times.length != 0) {
				jedis.expire(key, times[0]);
			}
		} catch (Exception e) {
			// 释放redis对象
			logger.info("" + e);
			returnJedis(jedis);
		} finally {
			// 返还到连接池
			returnResource(jedisPool, jedis);
		}

	}

	/**
	 * 设置数据
	 * 
	 * @param key
	 * @return
	 */
	public void setObject(byte[] key, byte[] value, int... times) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			if (times != null) {
				jedis.expire(key, times[0]);
			}
		} catch (Exception e) {
			// 释放redis对象
			logger.info("" + e);
			returnJedis(jedis);
		} finally {
			// 返还到连接池
			returnResource(jedisPool, jedis);
		}

	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = null;

		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
		} catch (Exception e) {
			// 释放redis对象
			logger.info("" + e);
			returnJedis(jedis);
		} finally {
			// 返还到连接池
			returnResource(jedisPool, jedis);
		}

		return value;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(byte[] key) {
		byte[] value = null;

		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
			return SerializeUtil.unSerialize(value);
		} catch (Exception e) {
			// 释放redis对象
			logger.info("" + e);
			returnJedis(jedis);
		} finally {
			// 返还到连接池
			returnResource(jedisPool, jedis);
		}

		return value;
	}

	/**
	 * 初始化切片池
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setTestOnBorrow(false);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

		logger.info("ip=" + ip + "----" + "port=" + port);
		shards.add(new JedisShardInfo(ip, port, "master"));

		// 构造池

		shardedJedisPool = new ShardedJedisPool(config, shards);

	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	@SuppressWarnings("deprecation")
	public void returnResource(JedisPool pool, Jedis redis) {
		if (redis != null) {
			pool.returnResourceObject(redis);
		}
	}

	@SuppressWarnings("deprecation")
	public void show() {

		jedisPool.returnResource(jedis);
		shardedJedisPool.returnResource(shardedJedis);

	}

}
