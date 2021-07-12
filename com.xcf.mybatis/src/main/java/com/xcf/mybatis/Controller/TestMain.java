package com.xcf.mybatis.Controller;

import java.sql.Timestamp;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
* @author xcf 
* @Date 创建时间：2021年6月15日 上午11:22:44 
*/
public class TestMain {
	private static Object obgObject=new Object();
	private static Lock lock=new ReentrantLock();
	public static  void main(String [] obj) {
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset()-24*60*60*1000*12;//今天零点零分零秒的毫秒数
		long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
		//long yesterday=System.currentTimeMillis()-24*60*60*1000;//昨天的这一时间的毫秒数
		//System.out.println(new Timestamp(current));//当前时间
		//System.out.println(new Timestamp(yesterday));//昨天这一时间点
		//System.out.println(new Timestamp(zero));//今天零点零分零秒
		//System.out.println(new Timestamp(twelve));//今天23点59分59秒
		//ThreadPoolExecutor executor=new ThreadPoolExecutor(5,10,10L,TimeUnit.SECONDS,new LinkedBlockingDeque<>(1000), new ThreadPoolExecutor.AbortPolicy());
		//ExecutorService executor = Executors.newSingleThreadExecutor();
		//CountDownLatch latch=new CountDownLatch(2);
		//executor.execute(()->{
		//	System.out.println("传进来的是：A");
		//	latch.countDown();
		//});
		
		//executor.execute(()->{
		//	System.out.println("传进来的是：B");
		//	latch.countDown();
		//});
		//try {
		//	latch.await();
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}
	    //System.out.println(executor.getActiveCount());	
	    //executor.isShutdown();
		new Thread(()->{
			for (int i = 0; i < 20; i++) {
				trydo(i);
			}
		},"t1" ).start();
			
		
		new Thread(()->{
			for (int i = 0; i < 20; i++) {
				trydo(i);
			}
		},"t2" ).start();
		
		//System.out.println("结束");
	}
	
	public static void sendinfo(String in) {
		System.out.println("传进来的是："+in);
	}
	
	public static void trydo(Integer num) {
		try {
			lock.lock();
				//obgObject.wait(1000);
				//Thread.sleep(1000);
				System.out.println( Thread.currentThread().getName()+"传进的信息:"+num);
			
		}catch(Exception e) {
			System.out.println("错误:"+e);
		}
		finally {
			//obgObject.notify();
			lock.unlock();
		}
		
	}

}
