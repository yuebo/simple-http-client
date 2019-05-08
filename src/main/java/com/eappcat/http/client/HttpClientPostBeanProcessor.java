package com.eappcat.http.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HttpClientPostBeanProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String,Object> beans=beanFactory.getBeansWithAnnotation(HttpClientsScan.class);
        HashSet<String> packages=new HashSet<>();
        for (String key:beans.keySet()){
            Object bean=beans.get(key);
            HttpClientsScan httpClientsScan=AnnotationUtils.findAnnotation(bean.getClass(),HttpClientsScan.class);
            if(httpClientsScan!=null){
                String p=bean.getClass().getPackage().getName();
                packages.add(p);
            }
        }

        if (packages.size()>0){
            Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
            scanner.setResourceLoader(this.applicationContext);
            scanner.scan(packages.toArray(new String[]{}));
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
