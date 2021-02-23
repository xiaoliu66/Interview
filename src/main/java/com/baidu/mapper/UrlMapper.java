package com.baidu.mapper;

import com.baidu.entity.Url;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UrlMapper extends BaseMapper<Url> {
    List<Integer> getUrlByRoleId(Integer roleId);
}
