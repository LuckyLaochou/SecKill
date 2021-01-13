package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class GoodsKeyPrefix extends BaseKeyPrefix {


    private GoodsKeyPrefix(String keyPrefix, int expireSeconds) {
        super(keyPrefix, expireSeconds);
    }

    public static GoodsKeyPrefix PREFIX_GOODSLIST = new GoodsKeyPrefix("GOODSLIST", 500);

    // 用来预减库存的
    public static GoodsKeyPrefix PREFIX_SECKILL_GOODSSTOCK = new GoodsKeyPrefix("SECKILL_GOODSSTOCK", 0);

    // 用来记录真实库存的
    public static GoodsKeyPrefix PREFIX_SQL_GOODSSTOCK = new GoodsKeyPrefix("SQL_GOODSSTOCK", 0);

}
