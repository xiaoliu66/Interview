package com.baidu.mapper;

import com.baidu.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {

    IPage<Map<String, Object>> selectRole(Page page);

    void addRole(@Param("roleId") Integer roleId,@Param("urlId") List<Integer> addTreeData);
}
