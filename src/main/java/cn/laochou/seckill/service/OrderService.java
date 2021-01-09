package cn.laochou.seckill.service;

import cn.laochou.seckill.dao.OrderDao;
import cn.laochou.seckill.pojo.OrderInfo;
import cn.laochou.seckill.pojo.SeckillOrder;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.util.DateUtil;
import cn.laochou.seckill.vo.GoodsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 订单服务
 */
@Service
public class OrderService {

    @Resource(name = "orderDao")
    private OrderDao orderDao;


    /**
     * 查找秒杀订单根据用户ID和商品ID
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @return 秒杀订单
     */
    public SeckillOrder getSeckillOrderByUserIdAndGoodsId(Long userId, int goodsId) {
        return orderDao.getSeckillOrderByUserIdAndGoodsId(userId, goodsId);
    }


    /**
     * 创建订单
     * @param user 用户
     * @param goodsVO 商品信息
     * @return 订单信息
     */
    @Transactional
    public OrderInfo createOrder(User user, GoodsVO goodsVO) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(DateUtil.dateToString());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVO.getId());
        orderInfo.setGoodsName(goodsVO.getGoodsName());
        orderInfo.setGoodsPrice(goodsVO.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insertOrder(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goodsVO.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);
        return orderInfo;
    }
}
