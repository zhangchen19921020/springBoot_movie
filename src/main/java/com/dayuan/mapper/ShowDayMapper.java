package com.dayuan.mapper;

import com.dayuan.entity.ShowDay;

import java.util.List;

public interface ShowDayMapper {

    int deleteByPrimaryKey(Integer id);


    ShowDay selectByPrimaryKey(Integer id);


    List<ShowDay> selectDateByMovieId(Integer movieId);

}