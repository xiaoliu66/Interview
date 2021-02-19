package com.baidu.controller;

import com.baidu.entity.Result;
import com.baidu.service.RoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public Result getAllRole() {
        List<Map<String,Object>> list = roleService.getAllRole();
        return new Result(true,"success",list);
    }

    /**
     * 分页查询
     * @param currentPage
     * @param PageSize
     * @return
     */
    @RequestMapping("/{currentPage}/{PageSize}")
    public Result getAllUser(@PathVariable("currentPage") Integer currentPage, @PathVariable("PageSize") Integer PageSize) {
        IPage<Map<String, Object>> allUserRole = roleService.selectRole(currentPage, PageSize);
        return new Result(true,"200",allUserRole);
    }

    /**
     * 添加用户
     * @param map
     * @return
     */
    @PostMapping("/addRole")
    public Result addRole(@RequestBody Map<String,Object> map) {
        roleService.addRole(map);
        return new Result(true,"200",null);
    }

    @PostMapping("/editRole")
    public Result editRole(@RequestBody Map<String,Object> map) {
        roleService.updateRole(map);
        return new Result(true,"200",null);
    }

    /**
     * 删除角色拥有的资源
     * @param roleId
     * @return
     */
    @DeleteMapping("/delete/{roleId}/{urlId}")
    public Result deleteRoleUrlById(@PathVariable("roleId") Integer roleId,@PathVariable("urlId") Integer urlId) {
        roleService.deleteRole(roleId,urlId);
        return new Result(true,"200",null);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @DeleteMapping("/deleteRoleById/{roleId}")
    public Result DeleteRoleById(@PathVariable("roleId") Integer roleId) {
        roleService.deleteRoleById(roleId);
        return new Result(true,"200",null);
    }

    /**
     * 关键字查找
     * @param map
     * @return
     */
    @PostMapping("/keyword")
    public Result getRoleByKeyword(@RequestBody Map<String,Object> map) {
        Integer currentPage = (Integer) map.get("currentPage");
        Integer pageSize = (Integer) map.get("pageSize");
        String keyword = (String) map.get("keyword");
        IPage<Map<String, Object>> list = roleService.getRoleByKeyword(currentPage,pageSize,keyword);
        return new Result(true,"200",list);
    }
}
