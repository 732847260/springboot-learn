package com.feng.springboot.springbootredisannotation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 11:44 2019/3/22
 * @Modified By: LiangZF
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
/**
 * maxInactiveIntervalInSeconds: 设置 Session 失效时间，使用 Redis Session 之后，原 Boot 的 server.session.timeout 属性不再生效。
 */
public class RedisSessionConfig {



}
