package com.sse.service.mq.rabbit;

import com.sse.config.RabbitConfig;
import com.sse.model.user.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RabbitMQ 消息生产者
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

    public void sendUser(User user) {
        mqTemplate.convertAndSend(RabbitConfig.ROUTE_KEY, user);
    }
}
