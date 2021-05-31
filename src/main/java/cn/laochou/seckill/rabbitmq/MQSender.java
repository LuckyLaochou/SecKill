package cn.laochou.seckill.rabbitmq;

import cn.laochou.seckill.config.MQConfig;
import cn.laochou.seckill.pojo.SeckillMessage;
import cn.laochou.seckill.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * MQ 发送者
 */
@Service
public class MQSender {

    private static final Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;


    /**
     * 我们通过Direct方式进行传递
     * @param seckillMessage
     */
    public void sendSeckillMessage(SeckillMessage seckillMessage) {
        String message = BeanUtil.beanToString(seckillMessage);
        logger.info(String.format("send message : %s", message));
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, message);
    }



























//    /**
//     * 下面都是 RabbitMQ测试代码， 与业务代码无关
//     * @param message
//     */
//    public void send(Object message) {
//        String m = BeanUtil.beanToString(message);
//        logger.info("send message : " + m);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE_NAME, m);
//    }
//
//
//    public void sendTopic(Object message) {
//        String m = BeanUtil.beanToString(message);
//        logger.info("send topic message : " + m);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, MQConfig.ROUTING_KEY1, m + " 1");
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, MQConfig.ROUTING_KEY2, m + " 2");
//    }
//
//    public void sendFanout(Object message) {
//        String m = BeanUtil.beanToString(message);
//        logger.info("send fanout message : " + m);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE_NAME, "", m);
//    }
//
//
//    public void sendHeaders(Object message) {
//        String m = BeanUtil.beanToString(message);
//        logger.info("send headers message : " + m);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("header1", "value1");
//        properties.setHeader("header2", "value2");
//        Message obj = new Message(m.getBytes(), properties);
//        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE_NAME, "", obj);
//    }



}
