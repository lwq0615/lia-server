package com.lia.system.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Redis {

    //redis地址
    @Value("${spring.redis.host}")
    private String host;

    //redis端口号
    @Value("${spring.redis.port}")
    private int port;

    //redis密码
    @Value("${spring.redis.password}")
    private String password;

    //默认数据库
    private static int defaultDB;

    //多个数据库集合
    @Value("${spring.redis.dbs}")
    private List<Integer> dbList;

    //RedisTemplate实例
    private static Map<Integer, RedisTemplate<String, Object>> redisTemplateMap = new HashMap<>();

    /**
     * 初始化连接池
     */
    @PostConstruct
    private void initRedisTemplate() {
        defaultDB = dbList.get(0);//设置默认数据库
        for (Integer db : dbList) {
            //存储多个RedisTemplate实例
            redisTemplateMap.put(db, redisTemplate(db));
        }
    }

    private LettuceConnectionFactory redisConnection(int db) {
        RedisStandaloneConfiguration server = new RedisStandaloneConfiguration();
        server.setHostName(host); // 指定地址
        server.setDatabase(db); // 指定数据库
        server.setPort(port); //指定端口
        server.setPassword(password); //指定密码
        LettuceConnectionFactory factory = new LettuceConnectionFactory(server);
        factory.afterPropertiesSet(); //刷新配置
        return factory;
    }

    //RedisTemplate模板
    private RedisTemplate<String, Object> redisTemplate(int db) {
        //为了开发方便，一般直接使用<String,Object>
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnection(db)); //设置连接
        //Json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 指定数据库进行切换
     * @param redisDb 数据库
     * @return
     */
    public static RedisTemplate<String, Object> getTemplate(RedisDb redisDb) {
        return redisTemplateMap.get(redisDb.dbIndex());
    }

    public static RedisTemplate<String, Object> getTemplate(int index) {
        return redisTemplateMap.get(index);
    }

    /**
     * 使用默认数据库
     *
     * @return
     */
    public static RedisTemplate<String, Object> getTemplate() {
        return redisTemplateMap.get(defaultDB);
    }
}