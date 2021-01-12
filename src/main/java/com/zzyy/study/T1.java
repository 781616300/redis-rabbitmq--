package com.zzyy.study;

import cn.hutool.core.util.IdUtil;
import com.zzyy.study.enums.OrderStatusEnum;

/**
 * @auther zzyy
 * @create 2020-10-25 14:12
 */
public class T1
{
    public static void main(String[] args)
    {
        System.out.println(IdUtil.fastSimpleUUID());
        System.out.println(System.currentTimeMillis());

        System.out.println(OrderStatusEnum.TIMEOUT_PAYMENT);
        System.out.println(OrderStatusEnum.TIMEOUT_PAYMENT.getRetCode());

    }
}
