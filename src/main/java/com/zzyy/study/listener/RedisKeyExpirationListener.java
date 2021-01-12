package com.zzyy.study.listener;

import com.zzyy.study.dao.OrderDao;
import com.zzyy.study.entities.Order;
import com.zzyy.study.enums.OrderStatusEnum;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2020-10-25 15:29
 */
//@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener
{
    @Resource
    private OrderDao orderDao;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer)
    {
        super(listenerContainer);
    }

    /**
     * orderToken---过期key，它到期后决定后续业务逻辑和流程
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern)
    {
        String expireKey_Token = message.toString();

        System.out.println("-----过了10秒钟到了，过期key： "+expireKey_Token);

        Order order = orderDao.getOrderByToken(expireKey_Token);

        //暂停毫秒，由于redis的数据还灭有传递回来，程序跑的太快。
        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }

        if (order == null) {
            return;
        }

        //过期超时订单走向
        if(order.getOrderStatus().equals(OrderStatusEnum.WAITTING_PAYMENT.getRetCode()))
        {
            orderDao.updateOrderStatus(expireKey_Token,OrderStatusEnum.TIMEOUT_PAYMENT.getRetCode());

            //后续逻辑订单超时，后续写库存加1，变回来....之前下单是减1，现在没有付款，写回库存，这步骤重要！！！
        }
        System.out.println("****该key"+expireKey_Token+"订单已经设置为已经超时，status=2: ");


    }


}
