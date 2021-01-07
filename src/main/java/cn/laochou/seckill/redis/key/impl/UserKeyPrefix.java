package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class UserKeyPrefix extends BaseKeyPrefix {

    public static final UserKeyPrefix PREFIX_BY_ID = new UserKeyPrefix("ID");


    private UserKeyPrefix(String keyPrefix) {
        super(keyPrefix);
    }

    @Override
    public String getKeyPrefix() {
        String className = this.getClass().getSimpleName();
        return String.format("%s:%s", className, this.getKeyPrefix());
    }
}
