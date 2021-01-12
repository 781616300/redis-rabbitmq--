package com.zzyy.study.service.impl;

import com.zzyy.study.dao.OrderDao;
import com.zzyy.study.entities.Order;
import com.zzyy.study.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * @auther zzyy
 * @create 2020-10-25 14:04
 */
@Service
public class OrderServiceImpl implements OrderService
{
    @Resource
    private OrderDao orderDao;

    @Override
    public int addOrder(Order order)
    {
        return orderDao.addOrder(order);
    }

    @Override
    public Order getOrderByToken(String orderToken)
    {
        return orderDao.getOrderByToken(orderToken);
    }

    @Override
    public Order getOrderBySerial(String orderSerial)
    {
        return orderDao.getOrderBySerial(orderSerial);
    }

    @Override
    public int updateOrderStatus(String orderToken, Integer orderStatus)
    {
        return orderDao.updateOrderStatus(orderToken,orderStatus);
    }

    @Override
    public int updateOrderStatusBySerial(String orderSerial, Integer orderStatus)
    {
        return orderDao.updateOrderStatusBySerial(orderSerial,orderStatus);
    }
}
