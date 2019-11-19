package cn.webapp.controller.zx;

import cn.common.consts.MqConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangYi on 2018/9/12
 **/
@Api(description = "rabbitMq")
@RestController
@Slf4j
public class RabbitMqController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/comm/rabbitMq",method = RequestMethod.POST)
    @ApiOperation(value = "rabbitMq测试")
    public Boolean sendMessage() {
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                rabbitTemplate.convertAndSend(MqConst.EXCHANGE_NAME, MqConst.ROUTING_KEY, i);
            }
        }).start();
        log.info("全部完成");
        return true;
    }
}

