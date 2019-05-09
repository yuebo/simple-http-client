package com.eappcat.http.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;

/**
 * @author yuebo
 */
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
                String [] packagesToScan=httpClientsScan.basePackages();
                for (String packageToScan:packagesToScan){
                    if(StringUtils.isEmpty(packageToScan)){
                        packageToScan=bean.getClass().getPackage().getName();
                    }
                    packages.add(packageToScan);
                }
            }
        }

        if (packages.size()>0){
            HttpClientScanner httpClientScanner = new HttpClientScanner((BeanDefinitionRegistry) beanFactory);
            httpClientScanner.setResourceLoader(this.applicationContext);
            httpClientScanner.scan(packages.toArray(new String[]{}));
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
