package cn.laochou.seckill.redis.key.base;


public class BaseKeyPrefix implements KeyPrefix {

    protected final String keyPrefix;

    protected int expireSeconds;

    public BaseKeyPrefix(String keyPrefix, int expireSeconds) {
        this.keyPrefix = keyPrefix;
        this.expireSeconds = expireSeconds;
    }

    @Override
    public String getKeyPrefix() {
        String className = this.getClass().getSimpleName();
        return String.format("%s:%s", className, this.keyPrefix);
    }

    @Override
    public int expireSeconds() {
        return this.expireSeconds;
    }
}
