package cn.laochou.seckill.redis.key.base;

import cn.laochou.seckill.redis.key.base.KeyPrefix;

public class BaseKeyPrefix implements KeyPrefix {

    private String keyPrefix;

    public BaseKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    public String getKeyPrefix() {
        return null;
    }
}
