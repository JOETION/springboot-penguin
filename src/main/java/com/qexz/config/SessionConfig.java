package com.qexz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 分布式session一致性,redis,30分钟
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
public class SessionConfig {

    /**
     * json格式存取session
     *
     * @return
     */
 /*   @Bean("springSessionDefaultRedisSerializer")
    public Jackson2JsonRedisSerializer redisSerializer() {
        return new Jackson2JsonRedisSerializer(Object.class);
    }*/

}
