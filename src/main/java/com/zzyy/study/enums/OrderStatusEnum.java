package com.zzyy.study.enums;

import lombok.Getter;

/**
 * @auther zzyy
 * @create 2020-10-25 13:38
 */
public enum OrderStatusEnum
{
    // 0待支付  1已支付 2已超时
    WAITTING_PAYMENT(0),OK_PAYMENT(1),TIMEOUT_PAYMENT(2);

    @Getter
    private Integer retCode;

    OrderStatusEnum(Integer retCode)
    {
        this.retCode = retCode;
    }
}
