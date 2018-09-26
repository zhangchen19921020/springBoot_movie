package com.dayuan.mapper;

import com.dayuan.entity.ShowTime;

import java.util.List;

public interface ShowTimeMapper {

    ShowTime selectByPrimaryKey(Integer id);


    List<ShowTime> selectShowTimeByDateId(Integer showDayId);

}