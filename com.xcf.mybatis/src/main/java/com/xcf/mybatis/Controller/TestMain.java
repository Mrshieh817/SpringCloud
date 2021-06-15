package com.xcf.mybatis.Controller;

import java.sql.Timestamp;
import java.util.TimeZone;

/** 
* @author xcf 
* @Date 创建时间：2021年6月15日 上午11:22:44 
*/
public class TestMain {
	public static  void main(String [] obj) {
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset()-24*60*60*1000*12;//今天零点零分零秒的毫秒数
		long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
		//long yesterday=System.currentTimeMillis()-24*60*60*1000;//昨天的这一时间的毫秒数
		//System.out.println(new Timestamp(current));//当前时间
		//System.out.println(new Timestamp(yesterday));//昨天这一时间点
		System.out.println(new Timestamp(zero));//今天零点零分零秒
		System.out.println(new Timestamp(twelve));//今天23点59分59秒
	}

}
