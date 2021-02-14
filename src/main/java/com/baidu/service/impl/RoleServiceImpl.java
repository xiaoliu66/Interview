package com.baidu.service.impl;

import com.baidu.entity.Role;
import com.baidu.mapper.RoleMapper;
import com.baidu.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Map<String, Object>> getAllRole() {
        List<Role> roleList = roleMapper.selectList(new QueryWrapper<Role>().select("id,role_name"));

        List<Map<String,Object>> list = new ArrayList<>();

        for (Role role : roleList) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("value",role.getId());
            map.put("label",role.getRoleName());
            list.add(map);
        }
        return list;
    }
}
