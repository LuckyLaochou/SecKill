package cn.laochou.seckill.rabbitmq;

import cn.laochou.seckill.config.MQConfig;
import cn.laochou.seckill.pojo.SeckillMessage;
import cn.laochou.seckill.pojo.SeckillOrder;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.service.GoodsService;
import cn.laochou.seckill.service.OrderService;
import cn.laochou.seckill.service.SeckillService;
import cn.laochou.seckill.util.BeanUtil;
import cn.laochou.seckill.vo.GoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * MQ 接受者
 */
@Service
public class MQReceiver {



    private static final Logger logger = LoggerFactory.getLogger(MQReceiver.class);


    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "seckillService")
    private SeckillService seckillService;



    @RabbitListener(queues = {MQConfig.SECKILL_QUEUE})
    public void receiveSeckillMessage(String message) {
        logger.info(String.format("receive seckill message : %s", message));
        SeckillMessage seckillMessage = BeanUtil.stringToBean(message, SeckillMessage.class);
        User user = seckillMessage.getUser();
        Long goodsId = seckillMessage.getGoodsId();
        // 判断库存
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        if(goodsVO.getStockCount() <= 0) return;
        // 判断是否已经秒杀到了
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(seckillOrder != null) return;
        // 减库存，下订单，写入秒杀订单
        seckillService.seckill(user, goodsVO);
    }














//    /**
//     * 以下是RabbitMQ的测试代码，与业务代码无关
//     * @param message
//     */
//
//
//    @RabbitListener(queues = {MQConfig.QUEUE_NAME})
//    public void receive(String message) {
//        logger.info("receive message : " + message);
//    }
//
//
//    @RabbitListener(queues = {MQConfig.TOPIC_QUEUE1_NAME})
//    public void receiveTopic1(String message) {
//        logger.info("receive topic1 message : " + message);
//    }
//
//    @RabbitListener(queues = {MQConfig.TOPIC_QUEUE2_NAME})
//    public void receiveTopic2(String message) {
//        logger.info("receive topic2 message : " + message);
//    }
//
//
//
//    @RabbitListener(queues = {MQConfig.HEADERS_QUEUE})
//    public void receiveHeaders(byte[] message) {
//        logger.info("receive headers message : " + new String(message, StandardCharsets.UTF_8));
//    }

}
