package com.dayuan.controller;


import com.dayuan.entity.Movie;
import com.dayuan.entity.ShowDay;
import com.dayuan.entity.ShowTime;
import com.dayuan.mapper.MovieMapper;
import com.dayuan.mapper.OrderMapper;
import com.dayuan.mapper.ShowDayMapper;
import com.dayuan.mapper.ShowTimeMapper;
import com.dayuan.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("api")
public class MovieController {

    @Resource
    private MovieMapper movieMapper;
    @Resource
    private ShowDayMapper showDayMapper;
    @Resource
    private ShowTimeMapper showTimeMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * index页面
     *
     * @return
     */


    @PostMapping("listMovies.do")
    @ResponseBody
    public AjaxResult listMovies() {

        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        Object listMovies = stringObjectValueOperations.get("listMovies");

        if (listMovies != null) {
            return AjaxResult.success(listMovies);
        }
        List<Movie> movieLists = movieMapper.listMovies();
        stringObjectValueOperations.set("listMovies", movieLists, 50, TimeUnit.SECONDS);
        return AjaxResult.success(movieLists);
    }

    /**
     * 电影详情
     *
     * @return
     */
    @PostMapping("getMovieInfo.do")
    @ResponseBody
    public AjaxResult getMovieInfo(@RequestParam("id") Integer id) {

        try {
            return AjaxResult.success(movieMapper.selectByPrimaryKey(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("getdDate.do")
    @ResponseBody
    public AjaxResult getdDate(@RequestParam("id") Integer id) {

        try {
            return AjaxResult.success(showDayMapper.selectDateByMovieId(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("getMovieTimeBydDate.do")
    @ResponseBody
    public AjaxResult getMovieTimeBydDate(@RequestParam("id") Integer id) {

        try {
            List<List<ShowTime>> showTimesByDate = new ArrayList<>();
            List<ShowDay> showDays = showDayMapper.selectDateByMovieId(id);

            for (ShowDay showDay : showDays) {
                List<ShowTime> showTimes = showTimeMapper.selectShowTimeByDateId(showDay.getId());
                showTimesByDate.add(showTimes);
            }
            return AjaxResult.success(showTimesByDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("getOrder.do")
    @ResponseBody
    public AjaxResult getOrder(@RequestParam("id") Integer id) {

        try {
            return AjaxResult.success(orderMapper.selectByShowTimeId(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
