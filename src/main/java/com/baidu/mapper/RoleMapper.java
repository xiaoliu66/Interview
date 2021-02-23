package com.baidu.mapper;

import com.baidu.entity.Role;
import com.baidu.entity.Url;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    IPage<Map<String, Object>> selectRole(Page page);

    void addRole(@Param("roleId") Integer roleId,@Param("urlId") List<Integer> urlIds);

    void deleteRoleUrl(Integer roleId, Integer urlId);

    void deleteRoleUrl2(Integer roleId);

    IPage<Map<String, Object>> selectRoleByKeyWord(Page page,String keyword);

    List<Url> getRole2Url(String role);
}
