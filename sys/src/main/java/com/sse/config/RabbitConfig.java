package com.sse.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-02-15 15:49
 */

@Slf4j
@Getter
@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${rabbitmq.cache.channel.size}")
    private int channelCacheSize;

    @Value("${rabbitmq.cache.channel.checkout-timeout}")
    private long channelCheckTimeout;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setChannelCacheSize(channelCacheSize);
        factory.setChannelCheckoutTimeout(channelCheckTimeout);
        factory.setPublisherConfirms(true);
        factory.setPublisherReturns(true);
        return factory;
    }

    @Bean("jsonRabbitTemplate")
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setMandatory(true);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("confirm message error! correlationData: {}, cause: {}", correlationData, cause);
            }
        });

        /*
         * 失败后隔 30 s 进行重试
         */
        template.setReturnCallback((message, replyCode, replyText, exchage, routingKey) -> {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.error("exchange: {}, routingKey: {}, message: {}, replyCode: {}, replyText: {}", exchage, routingKey, message, replyCode, replyText);
            template.send(exchage, routingKey, message);
        });
        return template;
    }


    /*************** direct route start **********/
    /*
    对列名
     */
    public static final String DIRECT_QUEUE_NAME = "directQueue";
    public static final String DIRECT_EXCHANGE_NAME = "directExchange";
    public static final String DIRECT_ROUTING_KEY = "direct.route.user";

    @Bean
    public Queue userQueue() {
        //创建一个对列名为变量 DIRECT_QUEUE_NAME 的对列
        return new Queue(DIRECT_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding bindingDirectExchange2UserQueue() {
        return BindingBuilder.bind(userQueue()).to(directExchange()).with(DIRECT_ROUTING_KEY);
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

    /*
    ---------------
    以下为接收者接收 json 格式的数据需要设置 message 转换器
     */

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
