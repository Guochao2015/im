package org.im.listener;

import org.apache.commons.lang3.StringUtils;
import org.im.annotation.HandlerInfo;
import org.im.annotation.Node;
import org.im.cache.FirstLevelCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;

@Component
public class BootInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    public BootInstantiationAwareBeanPostProcessor(){
        FirstLevelCache.setMap("Handler",new HashMap<>(100));
    }
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        /**
         * 添加处理 Packet 的 handler
         */
        HandlerInfo handlerInfo = bean.getClass().getAnnotation(HandlerInfo.class);
        if (!ObjectUtils.isEmpty(handlerInfo) && StringUtils.isNotBlank(handlerInfo.xmlns())){
            FirstLevelCache.get("Handler").put(handlerInfo.xmlns(),bean);
        }
        Node node = bean.getClass().getAnnotation(Node.class);
        if (!ObjectUtils.isEmpty(handlerInfo) && StringUtils.isNotBlank(node.element())){
            FirstLevelCache.get("Handler").put(node.element(),bean);
        }
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        return super.postProcessPropertyValues(pvs, pds, bean, beanName);
    }
}
