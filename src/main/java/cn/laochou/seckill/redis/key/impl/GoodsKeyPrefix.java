package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class GoodsKeyPrefix extends BaseKeyPrefix {


    private GoodsKeyPrefix(String keyPrefix, int expireSeconds) {
        super(keyPrefix, expireSeconds);
    }

    public static GoodsKeyPrefix PREFIX_GOODSLIST = new GoodsKeyPrefix("GOODSLIST", 500);

}
