package com.eappcat.http.client;

import org.springframework.beans.factory.FactoryBean;


import java.lang.reflect.Proxy;

public class HttpClientBeanFactory implements FactoryBean {
    private SimpleHttpClientProxy simpleHttpClientProxy;
    private Class clientClass;

    public void setSimpleHttpClientProxy(SimpleHttpClientProxy simpleHttpClientProxy) {
        this.simpleHttpClientProxy = simpleHttpClientProxy;
    }

    public void setClientClass(Class clientClass) {
        this.clientClass = clientClass;
    }

    @Override
    public Object getObject() throws Exception {
        Object proxy=Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{clientClass},simpleHttpClientProxy);
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return clientClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
