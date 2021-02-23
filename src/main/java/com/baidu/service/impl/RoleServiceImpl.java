package com.baidu.service.impl;

import com.baidu.entity.Role;
import com.baidu.entity.Url;
import com.baidu.mapper.RoleMapper;
import com.baidu.mapper.UrlMapper;
import com.baidu.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UrlMapper urlMapper;

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

    /**
     * 分页查询(自定义xml方式实现)
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Map<String, Object>> selectRole(Integer currentPage, Integer pageSize) {
        Page page = new Page(currentPage,pageSize);
        IPage<Map<String, Object>> iPage = roleMapper.selectRole(page);
        return iPage;
    }

    /**
     * 添加角色
     * @param map
     */
    @Override
    @Transactional
    public void addRole(Map<String, Object> map) {
        String rolename = (String) map.get("rolename");
        ArrayList<Integer> addTreeData = (ArrayList) map.get("addTreeData");

        Role role = new Role(null,rolename);
        roleMapper.insert(role);
        Integer roleId = role.getId();

        // 插入关联表中
        roleMapper.addRole(roleId,addTreeData);
    }

    /**
     * 删除角色拥有的资源
     * @param roleId
     * @param urlId
     */
    @Override
    @Transactional
    public void deleteRole(Integer roleId, Integer urlId) {
        roleMapper.deleteRoleUrl(roleId,urlId);
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Override
    @Transactional
    public void deleteRoleById(Integer roleId) {
        // 先删除角色表
        roleMapper.deleteById(roleId);
        // 再删除关联表
        roleMapper.deleteRoleUrl2(roleId);
    }

    /**
     * 修改角色
     * @param map
     */
    @Override
    @Transactional
    public void updateRole(Map<String, Object> map) {
        String rolename = (String) map.get("rolename");
        Integer roleId = (Integer) map.get("roleId");
        List<Integer> urlIds = (List<Integer>) map.get("urlIds");

        if (rolename.length() != 0) {
            Role role = new Role(roleId,rolename);
            roleMapper.updateById(role);

            // 先删除关联表中的，再插入到关联表中。
            roleMapper.deleteRoleUrl2(roleId);
            roleMapper.addRole(roleId,urlIds);
        }else {

            // 先删除关联表中的，再插入到关联表中。
            roleMapper.deleteRoleUrl2(roleId);
            roleMapper.addRole(roleId,urlIds);
        }
    }

    /**
     * 根据关键字查询
     * @param currentPage
     * @param pageSize
     * @param keyword
     * @return
     */
    public IPage<Map<String, Object>> getRoleByKeyword(Integer currentPage, Integer pageSize,String keyword) {
        Page page = new Page(currentPage,pageSize);
        IPage<Map<String, Object>> iPage = roleMapper.selectRoleByKeyWord(page,keyword);
        return iPage;
    }

    /**
     * 根据用户名获取相应地菜单列表
     * @param username
     * @return
     */
    @Override
    public List<Map<String, Object>> getMenuListByRoleName(String username) {
        List<Url> role2Url = roleMapper.getRole2Url(username);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Url url : role2Url) {
            Map<String, Object> map = new HashMap<>();
            map.put("path",url.getPath());
            map.put("title",url.getName());
            map.put("icon",url.getIcon());
            List<Url> children = url.getChildren();
            List<Map<String,Object>> mapList = new ArrayList<>();
            for (Url child : children) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("path",child.getPath());
                map1.put("title",child.getName());
                map1.put("linkUrl",child.getUrl());

                mapList.add(map1);
            }
            map.put("children",mapList);

            list.add(map);
        }
        return list;
    }
}
