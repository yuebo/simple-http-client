package com.eappcat.http.client;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import sun.misc.JavaNetHttpCookieAccess;

import java.net.CookieManager;
import java.net.CookiePolicy;

@Configuration
@ComponentScan
@HttpClientsScan
public class HttpClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CookieManager cookieManager(){
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return cookieManager;
    }
    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient(@Autowired HttpClientInterceptor httpClientInterceptor,@Autowired CookieManager cookieManager){
        return new OkHttpClient.Builder().addInterceptor(httpClientInterceptor).cookieJar(new JavaNetCookieJar(cookieManager)).build();
    }
    @Bean
    public SimpleHttpClientProxy simpleHttpClientProxy(@Autowired OkHttpClient client, @Autowired ConfigurableBeanFactory configurableBeanFactory){
        return new SimpleHttpClientProxy(client,configurableBeanFactory);
    }
}
