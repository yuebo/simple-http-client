package com.eappcat.http.client;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class HttpClientBeanFactory implements FactoryBean {
    private BeanFactory beanFactory;
    private Class clientClass;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setClientClass(Class clientClass) {
        this.clientClass = clientClass;
    }

    @Override
    public Object getObject() throws Exception {
        Object proxy=Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{clientClass},beanFactory.getBean(SimpleHttpClientProxy.class));
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
