package com.xcf.mybatis.Tool.es;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

/** 
* @author xcf 
* @Date 创建时间：2021年5月20日 下午2:24:45 
*/
@Slf4j
@Configuration
public class ElasticsearchRestConfigClient {

    @Autowired
    ElasticsearchRestConfigClientProperties config;

    private static final int ADDRESS_LENGTH = 2;
    private static final String HTTP_SCHEME = "http";
    private static final String HTTP_SCHEME_SSL = "https";

    public ElasticsearchRestConfigClient() {
    }


    @Bean(name = "highLevelClient")
    public RestHighLevelClient restClient() {
        if (CollUtil.isEmpty(config.getUris())) throw new NullPointerException("es host 配置不能为空");
        HttpHost[] hosts = config.getUris().stream()
            .map(this::makeHttpHost)
            .filter(Objects::nonNull)
            .toArray(HttpHost[]::new);
        RestHighLevelClient restClient = null;
        try {
            restClient = new RestHighLevelClient(
                RestClient.builder(
                    hosts)
                    .setHttpClientConfigCallback(httpClientBuilder -> {
                        httpClientBuilder.disableAuthCaching();
                        if (config.isSsl()){
                            // 信任所有
                            SSLContext sslContext = null;
                            try {
                                sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (KeyManagementException e) {
                                e.printStackTrace();
                            } catch (KeyStoreException e) {
                                e.printStackTrace();
                            }
                            SSLIOSessionStrategy sessionStrategy = new SSLIOSessionStrategy(sslContext, NoopHostnameVerifier.INSTANCE);
                            httpClientBuilder.setSSLStrategy(sessionStrategy);
                        }
                        if (StringUtils.isNotBlank(config.getUsername()) && StringUtils.isNotBlank(config.getPassword())){
                            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                            credentialsProvider.setCredentials(AuthScope.ANY,
                                new UsernamePasswordCredentials(config.getUsername(), config.getPassword()));
                            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                        return httpClientBuilder;
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restClient;
    }

    private HttpHost makeHttpHost(String s) {
        assert StringUtils.isNotEmpty(s);
        String[] address = s.split(":");
        String http = HTTP_SCHEME;
        if (config.isSsl()) http = HTTP_SCHEME_SSL;
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, http);
        } else {
            return null;
        }
    }

}