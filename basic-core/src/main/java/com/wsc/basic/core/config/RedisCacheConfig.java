package com.wsc.basic.core.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Parameter;
import java.time.Duration;

/**
 * 配置redis做为缓存配置
 * Spring内置的三大注解缓存是：
 * 1、Cacheable：缓存
 * 2、CacheEvict：删除缓存
 * 3、CachePut：更新缓存
 * <p>
 * 测试类TestCacheService
 *
 * @author 吴淑超
 * @date 2020-07-03 9:17
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * 缓存Key的生成规则:前缀+类名+方法名+所有参数
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(":");
            sb.append(target.getClass().getSimpleName());
            sb.append(".");
            sb.append(method.getName());
            sb.append(":");
            Parameter[] parameters = method.getParameters();
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    sb.append(parameters[i].getName());
                    sb.append("-");
                    sb.append(params[i]);
                    sb.append(".");
                }
            }
            return sb;
        };
    }

    /**
     * 缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 缓存基础配置
        RedisSerializer<?> serializer = serializer();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 缓存超时时间（单位为分钟，Duration.ofSeconds修改为秒）
                .entryTtl(Duration.ofMinutes(60))
                // Json序列化，redis保存需要把对象转成字符串
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                // 缓存Key的前缀，可以不加
                .computePrefixWith(cacheName -> "cache:" + cacheName)
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置value的序列化规则和 key的序列化规则
        RedisSerializer<?> serializer = serializer();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private RedisSerializer<?> serializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

}
