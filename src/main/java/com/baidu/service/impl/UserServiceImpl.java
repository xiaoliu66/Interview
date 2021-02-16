package com.baidu.service.impl;

import com.baidu.entity.User;
import com.baidu.mapper.UserMapper;
import com.baidu.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 查询所有用户及其所拥有的权限(分页查询)
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Map<String, Object>> getAllUserRole(Integer currentPage, Integer pageSize) {
        Page page = new Page(currentPage,pageSize);
        IPage<Map<String, Object>> allUserRole = userMapper.getAllUserRole(page);

        return allUserRole;
    }

    /**
     * 添加用户和相关角色
     * @param map
     */
    @Override
    @Transactional
    public void addUser(Map<String,Object> map) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String username = (String) map.get("username");
        String password = bCryptPasswordEncoder.encode(map.get("password").toString());
        String createtime = (String) map.get("createtime");
        User user = new User(null,username,password,createtime);
        userMapper.insert(user);
        // 获取user表中的主键
        Integer userId = user.getId();
        // 插入关联表中
        Integer roleId = (Integer) map.get("roleName");
        userMapper.insertUserRole(userId,roleId);
    }

    /**
     * 删除用户
     * @param userId
     */
    @Override
    @Transactional
    public void deleteUserById(Integer userId) {
        userMapper.deleteById(userId);
        userMapper.deleteUserRoleById(userId);
    }

    /**
     * 编辑用户
     * @param map
     */
    @Override
    @Transactional
    public void updateUser(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        String username = (String) map.get("username");
        String createtime = (String) map.get("createtime");
        Integer roleId = (Integer) map.get("roleId");
        User user = new User(userId,username,null,createtime);

        userMapper.updateById(user);
        // 更新关联表
        userMapper.deleteUserRoleById(userId);
        userMapper.insertUserRole(userId,roleId);
    }

    /**
     * 关键字查找
     * @param keyword
     * @return
     */
    @Override
    public Map<String, Object> getUserByKeyword(String keyword) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",keyword).or().eq("username",keyword);
        User user = userMapper.selectOne(queryWrapper);

        map.put("id",user.getId());
        map.put("username",user.getUsername());
        map.put("createtime",user.getCreatetime());

        if (user != null) {
            String role = userMapper.getRole(keyword);
            map.put("roleName",role);
        }

        return map;
    }


}
