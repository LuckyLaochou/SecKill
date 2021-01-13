package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class AccessKeyPrefix extends BaseKeyPrefix {


    public AccessKeyPrefix(String keyPrefix, int expireSeconds) {
        super(keyPrefix, expireSeconds);
    }

    // expireSeconds 是默认为 5秒
    public static final AccessKeyPrefix PREFIX_ACCESS = new AccessKeyPrefix("ACCESS", 5);


    public void updateExpireSeconds(int seconds) {
        this.expireSeconds = seconds;
    }

}
