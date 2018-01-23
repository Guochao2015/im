package org.im.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware {
    private  static ApplicationContext applicationContext = null;

    private static AutowireCapableBeanFactory autowireCapableBeanFactory = null;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContext.applicationContext = applicationContext;
        SpringApplicationContext.autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
    }
    public static Object getBean(String bean){
        return SpringApplicationContext.applicationContext.getBean(bean);
    }

    public static void destory(String bean){
        SpringApplicationContext.autowireCapableBeanFactory.destroyBean(bean);
    }

    public static <T> T getBean(Class<T> clazz){
        return SpringApplicationContext.applicationContext.getBean(clazz);
    }
}
