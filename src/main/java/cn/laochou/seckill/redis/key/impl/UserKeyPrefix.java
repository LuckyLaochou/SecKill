package cn.laochou.seckill.redis.key.impl;

import cn.laochou.seckill.redis.key.base.BaseKeyPrefix;

public class UserKeyPrefix extends BaseKeyPrefix {

    // 0 代表永久有效
    public static final UserKeyPrefix PREFIX_BY_ID = new UserKeyPrefix("ID", 0);
    public static final UserKeyPrefix PREFIX_BY_TOKEN = new UserKeyPrefix("TOKEN", 3600 * 24);


    private UserKeyPrefix(String keyPrefix, int expireSeconds) {
        super(keyPrefix, expireSeconds);
    }

}
