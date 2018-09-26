package com.dayuan.mapper;

import com.dayuan.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {


    Order selectByPrimaryKey(Integer id);

    List<Order> selectByShowTimeId(Integer showTimeId);

    List<String> listSaledSeats(Integer showTimeId);

    void insertOrder(Order order);

    void updateStatus(@Param("status") Integer status,
                      @Param("tradeNo") String tradeNo,
                      @Param("orderNo") String orderNo);

    Order selectByPrimaryKeyForLock(Integer id);

    List<Integer> listCancelOrderId();

    void updateStatusById(Integer id);

    int countBySeat(@Param("showTimeId") Integer showTimeId,
                    @Param("seat") String seat);


}

