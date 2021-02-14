package com.baidu.mapper;

import com.baidu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User> {

    // 查询用户名所拥有的权限
    public String getRole(String username);

    // 查询所有用户及其所拥有的权限
    //public List<Map<String,Object>> getAllUserRole();

    IPage<Map<String,Object>> getAllUserRole(Page page);

    // 插入关联表
    void insertUserRole(@PathParam("userId") Integer userId, @PathParam("roleId") Integer roleId);

    void deleteUserRoleById(Integer userId);
}
