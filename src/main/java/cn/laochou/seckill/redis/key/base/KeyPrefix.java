package cn.laochou.seckill.redis.key.base;

public interface KeyPrefix {

    String getKeyPrefix();

    int expireSeconds();

}
