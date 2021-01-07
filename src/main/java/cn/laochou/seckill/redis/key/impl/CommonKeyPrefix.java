package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class CommonKeyPrefix extends BaseKeyPrefix {

    public static final CommonKeyPrefix COMMON_KEY_PREFIX = new CommonKeyPrefix("");

    private CommonKeyPrefix(String keyPrefix) {
        super(keyPrefix);
    }
}
