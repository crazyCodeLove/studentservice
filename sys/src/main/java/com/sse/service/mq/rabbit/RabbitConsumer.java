package com.sse.service.mq.rabbit;

import com.sse.config.RabbitConfig;
import com.sse.model.user.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RabbitMQ 消息消费者
 * </p>
 * author pczhao  <br/>
 * date  2019-02-18 16:27
 */

@Service
public class RabbitConsumer {

    /**
     * 消费消息
     * @param user 接收到的消息
     */
    @RabbitListener(queues = RabbitConfig.DIRECT_QUEUE_NAME) //监听名 userAdd 的队列
    @RabbitHandler
    public void recived(User user) {
        System.out.println("received user:" + user);
    }

}
