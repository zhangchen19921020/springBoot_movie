package com.dayuan.mapper;

import com.dayuan.entity.OrderInfo;

public interface OrderInfoMapper {

    OrderInfo selectByPrimaryKey(Integer id);

    void insertOderInfo(OrderInfo orderInfo);

}