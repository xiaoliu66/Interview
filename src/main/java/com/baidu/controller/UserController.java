package com.baidu.controller;

import com.baidu.entity.Result;
import com.baidu.entity.User;
import com.baidu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/{currentPage}/{PageSize}")
    public Result getAllUser(@PathVariable("currentPage") Integer currentPage, @PathVariable("PageSize") Integer PageSize) {
        Map<String,Object> map = userService.getAllUser(currentPage,PageSize);
        return new Result(true,"200",map);
    }
}
