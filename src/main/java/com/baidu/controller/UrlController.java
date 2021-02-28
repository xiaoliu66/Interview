package com.baidu.controller;

import com.baidu.entity.Result;
import com.baidu.entity.Url;
import com.baidu.service.UrlService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/url")
@CrossOrigin
public class UrlController {
    @Autowired
    private UrlService urlService;

    /**
     * 查询所有的资源（按照前端树形控件的格式进行返回）
     * @return
     */
    @GetMapping
    public Result getAllUrl() {
        List<Map<String,Object>> list = urlService.getAllUrl();
        return new Result(true,"200",list);
    }

    /**
     * 根据角色获取资源列表
     * @param roleId
     * @return
     */
    @GetMapping("/{roleId}")
    public Result getUrlByRoleId(@PathVariable("roleId") Integer roleId) {
        List<Integer> list = urlService.getUrlByRoleId(roleId);
        return new Result(true,"200",list);
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/{currentPage}/{pageSize}")
    public Result getUrl(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        IPage<Url> list = urlService.getUrl(currentPage,pageSize);
        return new Result(true,"200",list);
    }

    /**
     * 关键字查询
     * @param currentPage
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/keyword/{currentPage}/{pageSize}")
    public Result getUrlByKeyWord(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize, @RequestParam String keyword) {
        IPage<Url> list = urlService.getUrl(currentPage,pageSize,keyword);
        return new Result(true,"200",list);
    }

    /**
     * 获取所有的父节点
     * @return
     */
    @GetMapping("/getAllParent")
    public Result getAllParent() {
        List<Map<String,Object>> list = urlService.getAllParent();
        return new Result(true,"200",list);
    }

    /**
     * 添加资源
     * @param url
     * @return
     */
    @PostMapping("/addUrl")
    public Result addUrl(@RequestBody Url url) {
        urlService.addUrl(url);
        return new Result(true,"200",null);
    }

    /**
     * 根据id删除资源
     * @param urlId
     * @return
     */
    @DeleteMapping("/delete/{urlId}")
    public Result deleteUrlById(@PathVariable("urlId") Integer urlId) {
        urlService.deleteUrlById(urlId);
        return new Result(true,"200",null);
    }
}
