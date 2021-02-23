package com.baidu.service.impl;

import com.baidu.entity.Url;
import com.baidu.mapper.UrlMapper;
import com.baidu.service.UrlService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UrlServiceImpl implements UrlService {
    @Autowired
    private UrlMapper urlMapper;

    /**
     * 查询所有的资源（按照前端树形控件的格式进行返回）
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllUrl() {
        List<Map<String, Object>> list = new ArrayList<>();

        QueryWrapper<Url> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_parent",1);

        List<Url> urls = urlMapper.selectList(queryWrapper);
        for (Url url : urls) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",url.getId());
            map.put("label",url.getName());
            map.put("children",new ArrayList<>());

            list.add(map);
        }

        QueryWrapper<Url> childrenQuery = new QueryWrapper<>();
        childrenQuery.eq("is_parent",0);

        List<Url> childernUrl = urlMapper.selectList(childrenQuery);


        for (Map<String, Object> map : list) {
            Integer id = (Integer) map.get("id");
            List children = (List) map.get("children");
            for (Url curl : childernUrl) {
                if (curl.getParentId() == id) {
                    Map<String,Object> childMap = new HashMap<>();

                    childMap.put("id",curl.getId());
                    childMap.put("label",curl.getName());
                    children.add(childMap);
                }
            }
        }
        return list;
    }

    /**
     * 根据角色id查询所拥有的的资源
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> getUrlByRoleId(Integer roleId) {
        return urlMapper.getUrlByRoleId(roleId);
    }
}
