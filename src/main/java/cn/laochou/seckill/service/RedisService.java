package cn.laochou.seckill.service;

import cn.laochou.seckill.redis.key.base.KeyPrefix;
import cn.laochou.seckill.redis.key.impl.CommonKeyPrefix;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.util.BeanUtil;
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
        String data = BeanUtil.beanToString(value);
        if(keyPrefix.expireSeconds() > 0) {
            // 设置过期时间
            jedis.setex(realKey, keyPrefix.expireSeconds(), data);
        }else {
            jedis.set(realKey, data);
        }
        // 给我们的key设置过期时间
//        jedis.expire(realKey, keyPrefix.expireSeconds());
        System.out.println(realKey);
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
            return null;
        }
        T t = (T) BeanUtil.stringToBean(value, clazz);
        // 释放资源
        jedis.close();
        return t;
    }


    /**
     * 从Redis中删除
     * @param keyPrefix
     * @param key
     * @return
     */
    public boolean delete(KeyPrefix keyPrefix, String key) {
        Jedis jedis = jedisPool.getResource();
        if(keyPrefix == null) {
            keyPrefix = CommonKeyPrefix.COMMON_KEY_PREFIX;
        }
        String realKey = String.format("%s:%s", keyPrefix.getKeyPrefix(), key);
        long result = jedis.del(realKey);
        jedis.close();
        return result > 0;
    }


    /**
     * 递减值
     * @param keyPrefix
     * @param key
     * @return
     */
    public long decr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = jedisPool.getResource();
        if(keyPrefix == null) {
            keyPrefix = CommonKeyPrefix.COMMON_KEY_PREFIX;
        }
        String realKey = String.format("%s:%s", keyPrefix.getKeyPrefix(), key);
        long result = jedis.decr(realKey);
        System.out.println(result);
        jedis.close();
        return result;
    }


    /**
     * 增加值
     * */
    public Long incr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = jedisPool.getResource();
        if(keyPrefix == null) {
            keyPrefix = CommonKeyPrefix.COMMON_KEY_PREFIX;
        }
        //生成真正的key
        String realKey  = String.format("%s:%s", keyPrefix.getKeyPrefix(), key);
        long result = jedis.incr(realKey);
        jedis.close();
        return result;
    }




}
