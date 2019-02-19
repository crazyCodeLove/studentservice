package com.sse.service.mq.rabbit;

import com.sse.config.RabbitConfig;
import com.sse.model.user.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RabbitMQ 消息生产者
 * 默认支持 String, byte[], Serializable
 * </p>
 * author pczhao  <br/>
 * date  2019-02-18 16:22
 */

@Service
public class RabbitProducer {

    private AmqpTemplate mqTemplate;

    @Autowired
    public RabbitProducer(AmqpTemplate mqTemplate) {
        this.mqTemplate = mqTemplate;
    }

    public void directSendUser(User user) {
        //第一个参数是对列名
        mqTemplate.convertAndSend(RabbitConfig.DIRECT_QUEUE_NAME, user);
    }

    /**
     * 向 topic 交换机发送消息
     * @param routeKey 路由键
     * @param message 消息
     */
    public void topicExchangeSend(String routeKey, String message) {
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是 routerKey topic.msg，第三个是你要发送的消息
        mqTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME, routeKey, message);
    }

}
