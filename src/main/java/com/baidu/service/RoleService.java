package com.baidu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Map<String, Object>> getAllRole();

    IPage<Map<String, Object>> selectRole(Integer currentPage, Integer pageSize);

    void addRole(Map<String, Object> map);

    void deleteRole(Integer roleId, Integer urlId);
}
