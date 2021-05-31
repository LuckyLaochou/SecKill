package cn.laochou.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 关于MQ 的一些配置信息
 */
@Configuration
public class MQConfig {


    public static final String SECKILL_QUEUE = "seckill.queue";





    @Bean
    public Queue queue() {
        return new Queue(SECKILL_QUEUE, true);
    }








































//    /**
//     * 下面都是RabbitMQ的测试代码，与业务代码无关
//     */
//
//    public static final String QUEUE_NAME = "queue";
//
//    public static final String TOPIC_QUEUE1_NAME = "topic.queue1";
//
//    public static final String TOPIC_QUEUE2_NAME = "topic.queue2";
//
//    public static final String HEADERS_QUEUE = "headers.queue";
//
//    public static final String TOPIC_EXCHANGE_NAME = "topic.exchange";
//
//    public static final String FANOUT_EXCHANGE_NAME = "fanout.exchange";
//
//    public static final String HEADERS_EXCHANGE_NAME = "headers.exchange";
//
//    public static final String ROUTING_KEY1 = "topic.key1";
//
//    public static final String ROUTING_KEY2 = "topic.#";
//
//    /**
//     * 这个是Direct模式， RabbitMQ的消息其实是发送到Exchange的
//     * @return
//     */
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE_NAME, true);
//    }
//
//
//    /**
//     * Topic 模式
//     */
//    @Bean
//    public Queue topicQueue1() {
//        return new Queue(TOPIC_QUEUE1_NAME, true);
//    }
//
//    @Bean
//    public Queue topicQueue2() {
//        return new Queue(TOPIC_QUEUE2_NAME, true);
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Binding topicBinding1() {
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
//    }
//
//    @Bean
//    public Binding topicBinding2() {
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
//    }
//
//
//    /**
//     * Fanout 模式
//     */
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Binding fanoutBinding1() {
//        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
//    }
//
//    @Bean
//    public Binding fanoutBinding2() {
//        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
//    }
//
//
//    /**
//     * Header 模式
//     */
//    @Bean
//    public HeadersExchange headersExchange() {
//        return new HeadersExchange(HEADERS_EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Queue headsQueue() {
//        return new Queue(HEADERS_QUEUE, true);
//    }
//
//    @Bean
//    public Binding headersBinding() {
//        Map<String, Object> map = new HashMap<String, Object>() {{
//            put("header1", "value1");
//            put("header2", "value2");
//        }};
//        return BindingBuilder.bind(headsQueue()).to(headersExchange()).whereAll(map).match();
//    }

}
