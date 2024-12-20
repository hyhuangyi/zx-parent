//package cn.webapp.configuration;
//
//import cn.common.consts.MqConst;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by huangYi on 2018/9/12
// **/
//@Configuration
//public class RabbitMQConfig {
//
//    // 创建队列
//    @Bean
//    public Queue queue() {
//        return new Queue(MqConst.QUEUE_NAME);
//    }
//
//    // 创建一个 topic 类型的交换器
//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(MqConst.EXCHANGE_NAME);
//    }
//
//    // 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）
//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(MqConst.ROUTING_KEY);
//    }
//
//    /**
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1", 5672);
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }**/
//
//    @Bean("rabbitTemplate")
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
//        //序列化
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        return rabbitTemplate;
//    }
//}
