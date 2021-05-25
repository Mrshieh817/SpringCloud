package com.xcf.mybatis.Tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import cn.hutool.crypto.digest.MD5;

/** 
* @author xcf 
* @Date 创建时间：2021年5月25日 上午9:40:55 
*/
public class MD5Util {
	  private final static String SRCSTR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    private final static String NUMBERS = "0123456789";
	    public final static String NOKEY = "a13730b36bf299397d96fe6a3a8a5195ef43363f55a3fc165213c52fdb2bcffa4d40c90eec2932746bb0b9753098cfdaec5dcaed8b325491006b75eca503ed77:DFSA54L3IYCHWE";

	    private final static Random RANDOM = new Random();

	    private static final String salt = "mrshieh@yeah.net";
	    private static final MD5 MD_5 = new MD5(salt.getBytes(), 32);

	    public static String encrypt(String source) {
	        if (StringUtils.isBlank(source)) {
	            throw new RuntimeException("Md5Utils:加密字符串不能为空");
	        }
	        return MD_5.digestHex(source);
	    }

	    public static boolean check(String source, String target) {
	        if (StringUtils.isBlank(source)|| StringUtils.isBlank(target)) {
	            throw new RuntimeException("Md5Utils:校对字符串不能为空");
	        }
	        return MD_5.digestHex(source).equals(target);
	    }

	    /**
	     * 得到一个长度为length的随机字符串
	     */
	    public static String getRandomString(int length) {
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            int number = RANDOM.nextInt(SRCSTR.length());
	            sb.append(SRCSTR.charAt(number));
	        }
	        return sb.toString();
	    }

	    public static String getRandomNumber(int length) {
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            int number = RANDOM.nextInt(NUMBERS.length());
	            sb.append(NUMBERS.charAt(number));
	        }
	        return sb.toString();
	    }

	    /**
	     * 获取0到指定范围的整数
	     */
	    public static int getRandomInt(int bound) {
	        return RANDOM.nextInt(bound);
	    }


	    public static String md5Password(String pwd, String salt) {
	        String temp = pwd + salt;
	        byte[] byteTemp;
	        try {
	            byteTemp = temp.getBytes("UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("不支持的编码", e);
	        }
	        return DigestUtils.md5DigestAsHex(byteTemp);
	    }


	    /**
	     * 32位大写MD5加密
	     * @param value
	     * @return
	     */
	    public static String md5(String value){
	        String result = null;
	        MessageDigest md5 = null;
	        try{
	            md5 = MessageDigest.getInstance("MD5");
	            md5.update((value).getBytes("UTF-8"));
	        }catch (NoSuchAlgorithmException error){
	            error.printStackTrace();
	        }catch (UnsupportedEncodingException e){
	            e.printStackTrace();
	        }
	        byte b[] = md5.digest();
	        int i;
	        StringBuffer buf = new StringBuffer("");

	        for(int offset=0; offset<b.length; offset++){
	            i = b[offset];
	            if(i<0){
	                i+=256;
	            }
	            if(i<16){
	                buf.append("0");
	            }
	            buf.append(Integer.toHexString(i));
	        }

	        result = buf.toString();
	        return result;
	    }

	    /**
	     * 得到一个UUID
	     */
	    public static String getUUIDString() {
	        return UUID.randomUUID().toString().replaceAll("-","");
	    }

}
