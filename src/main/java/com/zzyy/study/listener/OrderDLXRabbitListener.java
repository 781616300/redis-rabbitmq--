package com.zzyy.study.listener;

import com.zzyy.study.config.RabbitMQConfig;
import com.zzyy.study.dao.OrderDao;
import com.zzyy.study.entities.Order;
import com.zzyy.study.enums.OrderStatusEnum;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @auther zzyy
 * @create 2020-11-01 17:03
 */
@Component
public class OrderDLXRabbitListener
{
    @Resource
    private OrderDao orderDao;

    //监听死信队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_DLX_NAME)
    public void orderDLXRabbitListener(String orderSerial)
    {
        System.out.println("-------------OrderDLXRabbitListener come in:  "+orderSerial);

        Order order = orderDao.getOrderBySerial(orderSerial);
        if (order == null) {
            return;
        }

        //过期超时订单走向
        if(order.getOrderStatus().equals(OrderStatusEnum.WAITTING_PAYMENT.getRetCode()))
        {
            orderDao.updateOrderStatusBySerial(order.getOrderSerial(),OrderStatusEnum.TIMEOUT_PAYMENT.getRetCode());

            //后续逻辑订单超时，后续写库存加1，变回来....之前下单是减1，现在没有付款，写回库存，这步骤重要！！！
        }
        System.out.println("****该OrderSerial:   "+orderSerial+"\t 订单已经设置为已经超时，status=2: ");
    }
}
