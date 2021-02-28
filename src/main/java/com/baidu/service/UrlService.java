package com.baidu.service;

import com.baidu.entity.Url;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface UrlService {
    List<Map<String, Object>> getAllUrl();

    List<Integer> getUrlByRoleId(Integer roleId);

    IPage<Url> getUrl(Integer currentPage, Integer pageSize);

    IPage<Url> getUrl(Integer currentPage, Integer pageSize, String keyword);

    List<Map<String, Object>> getAllParent();

    void addUrl(Url url);

    void deleteUrlById(Integer urlId);
}
