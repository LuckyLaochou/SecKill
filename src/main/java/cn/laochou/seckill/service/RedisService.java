package cn.laochou.seckill.service;

import cn.laochou.seckill.redis.key.base.KeyPrefix;
import cn.laochou.seckill.redis.key.impl.CommonKeyPrefix;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.utils.BeanUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 存入Redis
     * @param key key
     * @param value value
     * @param <T> 泛型
     * @return 是否插入成功
     */
    public <T> Result<Boolean> set(KeyPrefix keyPrefix, String key, T value) {
        Jedis jedis = jedisPool.getResource();
        if(keyPrefix == null) {
            keyPrefix = CommonKeyPrefix.COMMON_KEY_PREFIX;
        }
        String realKey = String.format("%s:%s", keyPrefix.getKeyPrefix(), key);
        if(value == null) {
            return Result.error(CodeMessage.PARAM_ERROR);
        }
        // 参数类型判断
        if(value.getClass() == Integer.class) {
            jedis.set(realKey, String.format("%s", value));
        }else if(value.getClass() == String.class) {
            jedis.set(realKey, String.valueOf(value));
        }else if(value.getClass() == Long.class) {
            jedis.set(realKey, String.format("%s", value));
        }else {
            jedis.set(realKey, JSON.toJSONString(value));
        }
        // 释放资源
        jedis.close();
        return Result.success(Boolean.TRUE);
    }


    @SuppressWarnings("unchecked")
    public <T> T get(KeyPrefix keyPrefix, String key, Class<?> clazz) {
        Jedis jedis = jedisPool.getResource();
        if(keyPrefix == null) {
            keyPrefix = CommonKeyPrefix.COMMON_KEY_PREFIX;
        }
        String realKey = String.format("%s:%s", keyPrefix.getKeyPrefix(), key);
        String value = jedis.get(realKey);
        if(value == null || value.isEmpty()) {
            return (T) "";
        }
        T t = (T) BeanUtil.stringToBean(value, clazz);
        // 释放资源
        jedis.close();
        return t;
    }





}
