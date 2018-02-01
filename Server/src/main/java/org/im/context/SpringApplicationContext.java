package org.im.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SpringApplicationContext implements ApplicationContextAware {
    private  static AbstractApplicationContext applicationContext = null;

    private static AutowireCapableBeanFactory autowireCapableBeanFactory = null;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContext.applicationContext = (AbstractApplicationContext)applicationContext;
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

    public static void destoryContainer(){
        SpringApplicationContext.applicationContext.close();
    }

    @Bean
    public ExecutorService initExecutorService(){
        return Executors.newCachedThreadPool();
    }
}
