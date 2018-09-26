package com.dayuan.mapper;

import com.dayuan.entity.User;

public interface UserMapper {


    User selectByPrimaryKey(Integer id);

    User selectByLoginName(String loginName);

    void  insertUser (User user);
}