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
     * 消费 userAdd 的队列的消息
     * <p>
     * 接收到的消息
     */
    @RabbitListener(queues = RabbitConfig.DIRECT_QUEUE_NAME) //监听名 userAdd 的队列
    @RabbitHandler
    public void recived(User message) {
        System.out.println("[direct MQ] received user:" + message);
        System.out.println();
    }

    /**
     * 消费 topicQueueA 队列中的消息
     *
     * @param message 接收到的消息
     */
    @RabbitListener(queues = RabbitConfig.TOPIC_QUEUE_A_NAME) //监听名 topicQueueA 的队列
    @RabbitHandler
    public void topicQueueAComsume(String message) {
        System.out.println("[topic MQ A] received message:" + message);
    }

    @RabbitListener(queues = RabbitConfig.TOPIC_QUEUE_B_NAME) //监听名 topicQueueB 的队列
    @RabbitHandler
    public void topicQueueBComsume(String message) {
        System.out.println("[topic MQ B] received message:" + message);
    }

    @RabbitListener(queues = RabbitConfig.TOPIC_QUEUE_C_NAME) //监听名 topicQueueC 的队列
    @RabbitHandler
    public void topicQueueCComsume(String message) {
        System.out.println("[topic MQ C] received message:" + message);
    }
}
