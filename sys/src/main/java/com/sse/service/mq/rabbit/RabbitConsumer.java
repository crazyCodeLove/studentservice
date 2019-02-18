package com.sse.service.mq.rabbit;

import com.sse.model.user.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-02-18 16:27
 */

@Service
public class RabbitConsumer {

    @RabbitListener(queues = "userQueue") //监听名 userQueue 的队列
    @RabbitHandler
    public void recived(User user) {
        System.out.println("received user:" + user);
    }

}
