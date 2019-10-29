package com.xcf.mybatis.Tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import com.xcf.mybatis.Core.Resultmodel;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年10月28日 下午3:05:22 类说明
 */

public class HttpsUtils {
	/**
	 * Https请求
	 */
	private static final String HTTPS = "https";

	public static Resultmodel Get(String Url) {
		URL url;
		Resultmodel msg=new Resultmodel();
		HttpsURLConnection conn = null;
		try {
			// 如果是Https请求
			if (Url.startsWith(HTTPS)) {
				getTrust();
			}
			url = new URL(Url);
			conn = (HttpsURLConnection) url.openConnection();
			HttpsURLConnection.setFollowRedirects(true);
			CookieManager manager = new CookieManager();
			CookieHandler.setDefault(manager);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setUseCaches(false);// 设置不要缓存
			conn.setRequestProperty("User-agent",
					"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			StringBuffer sb = new StringBuffer();
			String readLine = new String();
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}
			responseReader.close();
			msg.setCode(conn.getResponseCode());
			msg.setMessage(sb.toString());
			

		} catch (Exception e) {
			msg.setCode(0);
			msg.setMessage(e.getMessage());
		}
		return msg;
	}

	/**
	 * 获取服务器信任
	 */
	private static void getTrust() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
