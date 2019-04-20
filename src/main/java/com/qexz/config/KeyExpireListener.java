package com.qexz.config;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/19          FXY        Created
 **********************************************
 */


import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class KeyExpireListener extends KeyExpirationEventMessageListener {
    public KeyExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        System.out.println("-------------,插入session了");
        super.doRegister(listenerContainer);
    }
}
