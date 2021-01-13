package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class SeckillKeyPrefix extends BaseKeyPrefix {

    public SeckillKeyPrefix(String keyPrefix, int expireSeconds) {
        super(keyPrefix, expireSeconds);
    }

    public static final SeckillKeyPrefix PREFIX_CREATEPATH = new SeckillKeyPrefix("CREATEPATH", 500);

}
