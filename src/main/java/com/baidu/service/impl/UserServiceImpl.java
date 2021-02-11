package com.baidu.service.impl;

import com.baidu.entity.User;
import com.baidu.mapper.UserMapper;
import com.baidu.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> getAllUser(int currentPage, int pageSize) {
        List<User> users = userMapper.selectList(null);
        IPage page = new Page(currentPage,pageSize);
        Wrapper wrapper = new QueryWrapper(new User(),"id,username,createtime");
        IPage selectPage = userMapper.selectPage(page, wrapper);
        List records = selectPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("list",records);
        map.put("currentPage",selectPage.getCurrent());
        map.put("pageSize",selectPage.getSize());
        map.put("total",selectPage.getTotal());

        return map;
    }
}
