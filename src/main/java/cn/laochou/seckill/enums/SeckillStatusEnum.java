package cn.laochou.seckill.enums;

public enum SeckillStatusEnum {

    SECKILL_NOT_BEGIN(0, "秒杀还没开始呢"),
    SECKILL_ING(1, "秒杀进行中"),
    SECKILL_END(2, "秒杀已经终止了");


    private final int status;

    private final String message;

    SeckillStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
