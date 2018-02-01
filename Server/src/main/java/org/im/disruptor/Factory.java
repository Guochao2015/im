package org.im.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;

@Component
public class Factory {

    private static final Logger LOGGER = LoggerFactory.getLogger(Factory.class);

    @Autowired
    ExecutorService executorService;

    @Autowired
    JIDOfflineFactory jIDOfflineFactory;

    @Autowired
    JIDOfflineHandler jIDOfflineHandler;

    @Autowired
    JIDOfflineProducer jidOfflineProducer;

    @PostConstruct
    public void initJidOffline(){
        LOGGER.info("init JIDOfflineFactory");
        int bufferSize = 1024;
        Disruptor<JIDOfflineFactory.JIDOffline> disruptor=
                                new Disruptor<JIDOfflineFactory.JIDOffline>(jIDOfflineFactory, bufferSize, (r) -> {
                                        Thread thread = new Thread(r);
                                        thread.setDaemon(true);
                                        return thread;
                                });
        disruptor.handleEventsWith(jIDOfflineHandler);

        disruptor.start();

        jidOfflineProducer.setRingBuffer(disruptor.getRingBuffer());
    }

}
