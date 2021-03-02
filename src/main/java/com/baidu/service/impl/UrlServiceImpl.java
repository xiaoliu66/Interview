package com.baidu.service.impl;

import com.baidu.entity.Url;
import com.baidu.mapper.UrlMapper;
import com.baidu.service.UrlService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Url> getUrl(Integer currentPage, Integer pageSize) {
        Page<Url> page = new Page<>(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("path");
        IPage<Url> urlIPage = urlMapper.selectPage(page,queryWrapper);
        return urlIPage;
    }

    /**
     * 关键字查询
     * @param currentPage
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public IPage<Url> getUrl(Integer currentPage, Integer pageSize, String keyword) {
        if (keyword.length() > 0) {
            Page<Url> page = new Page<>(currentPage,pageSize);
            QueryWrapper<Url> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",keyword).or().eq("name",keyword);
            queryWrapper.orderByAsc("path");
            IPage<Url> urlIPage = urlMapper.selectPage(page,queryWrapper);
            return urlIPage;
        }else {
            return getUrl(currentPage,pageSize);
        }
    }

    /**
     * 获取所有的父节点
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllParent() {
        QueryWrapper<Url> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id,name").eq("is_parent",1);
        List<Map<String, Object>> mapList = urlMapper.selectMaps(queryWrapper);
        return mapList;
    }

    /**
     * 添加资源
     * @param url
     */
    @Override
    @Transactional
    public void addUrl(Url url) {
        if (Integer.parseInt(url.getIsParent()) == 0) {
            QueryWrapper<Url> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("max(priority) as priority").eq("parent_id",url.getParentId());
            Url one = urlMapper.selectOne(queryWrapper);

            QueryWrapper<Url> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",url.getParentId());
            Url parent = urlMapper.selectOne(queryWrapper1);

            // 如果父节点是新增加的，则one 是Null
            if (one == null) {
                url.setPriority(1);
                url.setPath(parent.getPath()+ "-" + 1) ;
                url.setUrl("/html/" + url.getUrl());
                urlMapper.insert(url);
            }else {
                url.setPriority(one.getPriority() + 1);
                url.setPath(parent.getPath() + "-" + (one.getPriority()+1) );
                url.setUrl("/html/" + url.getUrl());
                urlMapper.insert(url);
            }

        }else {
            QueryWrapper<Url> urlQueryWrapper = new QueryWrapper<>();
            urlQueryWrapper.select("max(substring(path,1,1)) as path").eq("is_parent",1);
            Url one = urlMapper.selectOne(urlQueryWrapper);


            url.setPath(Integer.parseInt(one.getPath()) + 1 + "");
            url.setPriority(Integer.parseInt(one.getPath()) + 1);

            urlMapper.insert(url);
        }

    }

    /**
     * 根据ID删除资源
     * @param urlId
     */
    @Override
    @Transactional
    public void deleteUrlById(Integer urlId) {
        urlMapper.deleteById(urlId);
    }

    /**
     * 修改资源
     * @param url
     */
    @Override
    @Transactional
    public void updateUrl(Url url) {
        urlMapper.updateById(url);
    }
}
