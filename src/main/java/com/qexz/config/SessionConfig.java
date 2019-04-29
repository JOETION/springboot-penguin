package com.qexz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 分布式session一致性,redis,半天
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 6*60*60)
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
