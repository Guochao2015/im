package org.im.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.springframework.stereotype.Component;

@Component
public class JIDOfflineProducer {
    private RingBuffer<JIDOfflineFactory.JIDOffline> ringBuffer;

/*
    public RingBuffer<JIDOfflineFactory.JIDOffline> getRingBuffer() {
        return ringBuffer;
    }
*/

    public void setRingBuffer(RingBuffer<JIDOfflineFactory.JIDOffline> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void push(String jid){
        long sequence = ringBuffer.next();
        try{
            JIDOfflineFactory.JIDOffline jidEvent = ringBuffer.get(sequence);
            jidEvent.setJid(jid);
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
