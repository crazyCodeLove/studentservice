package com.sse.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-02-15 15:49
 */

@Configuration
public class RabbitConfig {

    /*************** direct route start **********/
    /*
    对列名
     */
    public static final String DIRECT_QUEUE_NAME = "directQueue";

    @Bean
    public Queue userQueue() {
        //创建一个对列名为变量 DIRECT_QUEUE_NAME 的对列
        return new Queue(DIRECT_QUEUE_NAME);
    }

    /*************** topic route start **********/
    /*
    话题对列名
     */
    public static final String TOPIC_QUEUE_A_NAME = "topicQueueA";
    public static final String TOPIC_QUEUE_B_NAME = "topicQueueB";
    public static final String TOPIC_QUEUE_C_NAME = "topicQueueC";
    public static final String TOPIC_EXCHANGE_NAME = "topicExchangeA";

    // accurate 的 route key
    private static final String TOPIC_ROUTE_KEY_ACC = "topic.msg";
    // simple 的 route key
    private static final String TOPIC_ROUTE_KEY_SIM = "topic.*";
    // multiple result
    private static final String TOPIC_ROUTE_KEY_MUL = "topic.#.end";


    @Bean
    public Queue topicQueueA() {
        return new Queue(TOPIC_QUEUE_A_NAME);
    }

    @Bean
    public Queue topicQueueB() {
        return new Queue(TOPIC_QUEUE_B_NAME);
    }

    @Bean
    public Queue topicQueueC() {
        return new Queue(TOPIC_QUEUE_C_NAME);
    }

    @Bean
    public TopicExchange topicExchange() {
        // 创建一个名为变量 TOPIC_EXCHANGE_NAME 的交换器
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    /**
     * 将定义的 topicA 队列与 topicExchange 交换机绑定，路由 key 为 TOPIC_ROUTE_KEY_ACC
     */
    @Bean
    public Binding bindingQueueAExchangeWithTopicAcc() {
        // 第一个参数为 对列， 第二个参数为 交换机， 第三个参数为 路由键
        // topicA的key为topic.msg 那么他只会接收包含topic.msg的消息
        return BindingBuilder.bind(topicQueueA()).to(topicExchange()).with(TOPIC_ROUTE_KEY_ACC);
    }

    @Bean
    public Binding bindingQueueBExchangeWithTopicSim() {
        // 第一个参数为 对列， 第二个参数为 交换机， 第三个参数为 路由键
        // topicB 的 key 为 topic.* 那么他只会接收 topic 开头的，长度为 2 的消息
        return BindingBuilder.bind(topicQueueB()).to(topicExchange()).with(TOPIC_ROUTE_KEY_SIM);
    }

    @Bean
    public Binding bindingQueueCExchangeWithTopicMul() {
        // 第一个参数为 对列， 第二个参数为 交换机， 第三个参数为 路由键
        // topicC 的key为 topic.#.end 那么他只会接收类似于 topic.B.end 这样格式的消息
        return BindingBuilder.bind(topicQueueC()).to(topicExchange()).with(TOPIC_ROUTE_KEY_MUL);
    }

}
