package org.kjtc.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate){
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
//        cacheManager.setDefaultExpiration(10000);
        return cacheManager;
    }

    // SpringBoot提供了对Redis的自动配置功能
    // 在RedisAutoConfiguration中默认为我们配置了JedisConnectionFactory（客户端连接）
    // RedisTemplate（数据操作模板）默认采用JdkSerializationRedisSerializer的二进制数据序列化方式
    // StringRedisTemplate（数据操作模板）只针对键值对都是字符型的数据进行操作
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        RedisSerializer<String> strRedisSerializer = new StringRedisSerializer();// Long类型不可以会出现异常信息;
//        RedisSerializer<Object> objRedisSerializer = new RedisObjectSerializer();// Long类型不可以会出现异常信息;
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(strRedisSerializer);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //使用自定义RedisObjectSerializer来序列化和反序列化redis的value值
//        redisTemplate.setValueSerializer(objRedisSerializer);
//        redisTemplate.setHashKeySerializer(strRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
