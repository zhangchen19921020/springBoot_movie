package com.dayuan.mapper;

import com.dayuan.entity.Movie;

import java.util.List;

public interface MovieMapper {


    Movie selectByPrimaryKey(Integer id);

    List<Movie> listMovies();


}