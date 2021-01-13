package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class OrderKeyPrefix extends BaseKeyPrefix {

    private OrderKeyPrefix(String keyPrefix, int expireSeconds) {
        super(keyPrefix, expireSeconds);
    }

    public static final OrderKeyPrefix PREFIX_BY_USERID_GOODSID = new OrderKeyPrefix("USERID_GOODSID", 500);

}
