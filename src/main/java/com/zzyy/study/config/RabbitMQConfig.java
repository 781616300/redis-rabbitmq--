package com.zzyy.study.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyy
 * @create 2020-10-31 14:30
 */
@Configuration
public class RabbitMQConfig
{
    //正常交换机名称常量
    public static final String EXCHANGE_NAME = "cloud_study_exchange";
    //正常队列名称常量
    public static final String QUEUE_NAME = "cloud_study_queue";
    //死信交换机名称常量
    public static final String EXCHANGE_DLX_NAME = "cloud_study_dlx_exchange";
    //死信队列名称常量
    public static final String QUEUE_DLX_NAME = "cloud_study_dlx_queue";

    //1 正常交换机
    @Bean("bootExchange")
    public Exchange bootExchange()
    {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //2 正常队列
    @Bean("bootQueue")
    public Queue bootQueue()
    {

        //return QueueBuilder.durable(QUEUE_NAME).build(); //正常绑定

        /**
         * 正常队列关联死信队列，正常的超时了去哪里？
         */
        return QueueBuilder.durable(QUEUE_NAME).deadLetterExchange(EXCHANGE_DLX_NAME).deadLetterRoutingKey("boot.abc").build();
    }
    //3 交换机和队列绑定
    @Bean
    public Binding bindingQueueExchange(@Qualifier("bootQueue") Queue queue,@Qualifier("bootExchange") Exchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

    //4 死信交换机
    @Bean("bootDLXExchange")
    public Exchange bootDLXExchange()
    {
        return ExchangeBuilder.topicExchange(EXCHANGE_DLX_NAME).durable(true).build();
    }

    //5 死信队列
    @Bean("bootDLXQueue")
    public Queue bootDLXQueue()
    {
        return QueueBuilder.durable(QUEUE_DLX_NAME).build();
    }

    //6 死信交换机和死信队列绑定
    @Bean
    public Binding bindingDLXQueueExchange(@Qualifier("bootDLXQueue") Queue queue,@Qualifier("bootDLXExchange") Exchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

}
