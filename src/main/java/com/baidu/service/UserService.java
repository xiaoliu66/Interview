package com.baidu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> getAllUser(int currentPage, int pageSize);

    // 查询所有用户及其所拥有的权限
    public IPage<Map<String, Object>> getAllUserRole(Integer currentPage, Integer pageSize);
}
