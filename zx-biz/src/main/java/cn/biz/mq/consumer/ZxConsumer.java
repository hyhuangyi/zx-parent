package cn.biz.mq.consumer;

import cn.common.consts.MqConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ZxConsumer {
    @RabbitListener(queues = MqConst.QUEUE_NAME)
    public void consumeMessage(Message message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("ZxConsumer 异常",e);
        }
       log.info(message.toString());
    }
}
