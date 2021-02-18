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
@Transactional
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
    public void addRole(Map<String, Object> map) {
        String rolename = (String) map.get("rolename");
        ArrayList<Integer> addTreeData = (ArrayList) map.get("addTreeData");

        // 现获取所有父节点的id
        QueryWrapper<Url> queryWrapper = new QueryWrapper<>(new Url(),"id");
        queryWrapper.eq("is_parent", 1);
        List<Url> urls = urlMapper.selectList(queryWrapper);

        List<Integer> urlIdList = new ArrayList<>();

        // 去除父节点
        for (Integer addTreeDatum : addTreeData) {
            int i = 0;
            for (Url url : urls) {
                if (url.getId() == addTreeDatum) {
                    break;
                }else {
                    i++;
                    if (i == 4) urlIdList.add(addTreeDatum);
                }
            }
        }

        Role role = new Role(null,rolename);
        roleMapper.insert(role);
        Integer roleId = role.getId();

        // 插入关联表中
        roleMapper.addRole(roleId,urlIdList);
    }

    /**
     * 删除
     * @param roleId
     * @param urlId
     */
    @Override
    public void deleteRole(Integer roleId, Integer urlId) {

    }
}
