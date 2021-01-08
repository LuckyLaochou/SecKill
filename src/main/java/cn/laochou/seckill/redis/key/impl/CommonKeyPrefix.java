package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class CommonKeyPrefix extends BaseKeyPrefix {

    public static final CommonKeyPrefix COMMON_KEY_PREFIX = new CommonKeyPrefix("", 500);

    private CommonKeyPrefix(String keyPrefix, int expireSeconds) {
        super(keyPrefix, expireSeconds);
    }
}
