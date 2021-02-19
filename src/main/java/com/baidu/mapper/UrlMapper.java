package com.baidu.mapper;

import com.baidu.entity.Url;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface UrlMapper extends BaseMapper<Url> {
    List<Integer> getUrlByRoleId(Integer roleId);
}
