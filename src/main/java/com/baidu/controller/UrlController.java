package com.baidu.controller;

import com.baidu.entity.Result;
import com.baidu.service.UrlService;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/url")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @GetMapping
    public Result getAllUrl() {
        List<Map<String,Object>> list = urlService.getAllUrl();
        return new Result(true,"200",list);
    }
}
