package com.eappcat.http.client;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@HttpClientsScan
public class HttpClientConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder().build();
    }
    @Bean
    public SimpleHttpClientProxy simpleHttpClientProxy(@Autowired OkHttpClient client){
        return new SimpleHttpClientProxy(client);
    }
}
