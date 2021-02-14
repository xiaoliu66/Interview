package com.baidu.controller;

import com.baidu.entity.Result;
import com.baidu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
