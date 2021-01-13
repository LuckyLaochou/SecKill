package cn.laochou.seckill.service;

import cn.laochou.seckill.enums.SeckillStatusEnum;
import cn.laochou.seckill.pojo.OrderInfo;
import cn.laochou.seckill.pojo.SeckillOrder;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.redis.key.impl.SeckillKeyPrefix;
import cn.laochou.seckill.util.MD5Util;
import cn.laochou.seckill.util.UUIDUtil;
import cn.laochou.seckill.vo.GoodsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 秒杀服务
 */
@Service
public class SeckillService {

    /**
     * 一般在各自的Service引入各自的dao
     * 但是如果一个Service想使用其他服务，一般也是引入其他的Service
     */
    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "redisService")
    private RedisService redisService;

    /**
     * 事务性
     * 1. 减库存
     * 2. 下订单
     * 3. 写入秒杀订单
     * @param user 用户信息
     * @param goodsVO 商品信息
     * @return 订单信息
     */
    @Transactional
    public OrderInfo seckill(User user, GoodsVO goodsVO) {
        // 1. 减库存，并判断是否减库存成功
        boolean reduce = goodsService.reduceStock(goodsVO.getId());
        if(reduce) {
            // 下订单 包含两个表 order_info seckill_order
            return orderService.createOrder(user, goodsVO);
        }
        return null;
    }


    public Long getSeckillResult(Long userId, Long goodsId) {
        // 查询我们的订单表是否存在
        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(userId, goodsId);
        // 如果查找了，就说明秒杀成功
        if(order != null) return order.getOrderId();
        // 下面分为两种情况
            // 1. 秒杀失败
        int stockCount = goodsService.getGoodsStockById(goodsId);
        if(stockCount <= 0) return (long) SeckillStatusEnum.SECKILL_FAIL.getStatus();
            // 2. 秒杀排队中
        return (long) SeckillStatusEnum.SECKILL_BE_QUEUEING.getStatus();
    }

    public String createSeckillPath(User user, Long goodsId) {
        String path = MD5Util.MD5(String.format("%s%s%s", user.getId(), UUIDUtil.getUUID(), goodsId));
        redisService.set(SeckillKeyPrefix.PREFIX_CREATEPATH, String.format("%s_%s", user.getId(), goodsId), path);
        return path;
    }

    public boolean checkPath(User user, Long goodsId, String path) {
        String pathInRedis = redisService.get(SeckillKeyPrefix.PREFIX_CREATEPATH, String.format("%s_%s", user.getId(), goodsId), String.class);
        return path.equals(pathInRedis);
    }
}
