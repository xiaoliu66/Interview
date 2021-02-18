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
    public Result addUser(@RequestBody Map<String,Object> map) {
        roleService.addRole(map);
        return new Result(true,"200",null);
    }

    /*@PostMapping("/editUser")
    public Result editUser(@RequestBody Map<String,Object> map) {
        roleService.updateUser(map);
        return new Result(true,"200",null);
    }*/

    /**
     * 删除
     * @param roleId
     * @return
     */
    @DeleteMapping("/delete/{roleId}/{urlId}")
    public Result deleteUserById(@PathVariable("roleId") Integer roleId,@PathVariable("urlId") Integer urlId) {
        roleService.deleteRole(roleId,urlId);
        return new Result(true,"200",null);
    }

    /**
     * 关键字查找
     * @param keyword
     * @return
     */
    /*@RequestMapping
    public Result getUserByKeyword(@RequestParam String keyword) {
        Map<String,Object> map = roleService.getUserByKeyword(keyword);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(map);
        return new Result(true,"200",list);
    }*/
}
