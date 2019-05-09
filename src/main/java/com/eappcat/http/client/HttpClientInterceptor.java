package com.eappcat.http.client;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpClientInterceptor implements Interceptor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Class c=(Class)chain.request().tag();
        SimpleClient simpleClient= AnnotationUtils.findAnnotation(c,SimpleClient.class);
        Class<? extends HttpInterceptor> interceptor=simpleClient.interceptor();
        if(applicationContext.getBeansOfType(interceptor).isEmpty()){
            return chain.proceed(chain.request());
        }
        Interceptor interceptorAdapter=applicationContext.getBean(interceptor);
        return interceptorAdapter.intercept(chain);
    }
}
