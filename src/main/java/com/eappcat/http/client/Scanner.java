package com.eappcat.http.client;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Set;

public class Scanner extends ClassPathBeanDefinitionScanner {

    public Scanner(BeanDefinitionRegistry registry) {
        super(registry);
    }
    public void registerDefaultFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(SimpleClient.class));
    }
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions =   super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

            definition.getPropertyValues().add("clientClass", definition.getBeanClassName());
            definition.getPropertyValues().add("simpleHttpClientProxy", ((ConfigurableListableBeanFactory)getRegistry()).getBean(SimpleHttpClientProxy.class));
            definition.setBeanClass(HttpClientBeanFactory.class);
        }
        return beanDefinitions;
    }
    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata()
                .hasAnnotation(SimpleClient.class.getName());
    }

}
