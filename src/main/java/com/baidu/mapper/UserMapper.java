package com.baidu.mapper;

import com.baidu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    // 查询用户名所拥有的权限
    public String getRole(String username);
}
