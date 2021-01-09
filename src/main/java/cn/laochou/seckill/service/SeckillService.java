package cn.laochou.seckill.service;

import cn.laochou.seckill.pojo.OrderInfo;
import cn.laochou.seckill.pojo.User;
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
        // 1. 减库存
        goodsService.reduceStock(goodsVO.getId());
        // 下订单 包含两个表 order_info seckill_order
        return orderService.createOrder(user, goodsVO);
    }


}
