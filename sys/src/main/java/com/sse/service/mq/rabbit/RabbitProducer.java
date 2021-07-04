package com.sse.service.mq.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.config.RabbitConfig;
import com.sse.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * RabbitMQ 消息生产者
 * 默认支持 String, byte[], Serializable
 * </p>
 * author pczhao  <br/>
 * date  2019-02-18 16:22
 */

@Slf4j
@Service
public class RabbitProducer {

    private final RabbitTemplate mqTemplate;
    private RabbitConfig rabbitConfig;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public RabbitProducer(@Qualifier("jsonRabbitTemplate") RabbitTemplate mqTemplate, RabbitConfig rabbitConfig) {
        this.mqTemplate = mqTemplate;
        this.rabbitConfig = rabbitConfig;
    }

    public <T> void send(String exchangeName, String routingKey, T message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        try {
            log.info("start convert and send. correlationData: {}, message: {}", correlationData, mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        mqTemplate.convertAndSend(exchangeName, routingKey, message, correlationData);
    }

    public void directSendUser(User user) {
        mqTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE_NAME, RabbitConfig.DIRECT_ROUTING_KEY, user);
    }

    /**
     * 向 topic 交换机发送消息
     *
     * @param routeKey 路由键
     * @param message  消息
     */
    public void topicExchangeSend(String routeKey, String message) {
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是 routerKey topic.msg，第三个是你要发送的消息
        send(RabbitConfig.TOPIC_EXCHANGE_NAME, routeKey, message);
    }

}
