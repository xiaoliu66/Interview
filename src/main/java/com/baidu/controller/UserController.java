package com.baidu.controller;

import com.baidu.entity.Result;
import com.baidu.entity.User;
import com.baidu.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询
     * @param currentPage
     * @param PageSize
     * @return
     */
    @RequestMapping("/{currentPage}/{PageSize}")
    public Result getAllUser(@PathVariable("currentPage") Integer currentPage, @PathVariable("PageSize") Integer PageSize) {
        IPage<Map<String, Object>> allUserRole = userService.getAllUserRole(currentPage, PageSize);
        return new Result(true,"200",allUserRole);
    }

    /**
     * 添加用户
     * @param map
     * @return
     */
    @PostMapping("/addUser")
    public Result addUser(@RequestBody Map<String,Object> map) {
        userService.addUser(map);
        return new Result(true,"200",null);
    }

    @PostMapping("/editUser")
    public Result editUser(@RequestBody Map<String,Object> map) {
        userService.updateUser(map);
        return new Result(true,"200",null);
    }

    @DeleteMapping("/delete/{userId}")
    public Result deleteUserById(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
        return new Result(true,"200",null);
    }

    /**
     * 关键字查找
     * @param keyword
     * @return
     */
    @RequestMapping
    public Result getUserByKeyword(@RequestParam String keyword) {
        Map<String,Object> map = userService.getUserByKeyword(keyword);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(map);
        return new Result(true,"200",list);
    }
}
