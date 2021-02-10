package com.baidu.controller;

import com.baidu.entity.InterviewList;
import com.baidu.entity.Result;
import com.baidu.service.InterviewListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/InterviewListDetail")
public class InterviewListDetailController {

    @Autowired
    private InterviewListService interviewListService;

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result getAll(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        try {
            Map<String, Object> map = interviewListService.getAllInterviewList(currentPage,pageSize);
            return new Result(true,"200",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"500",null);
        }
    }

    /**
     * 根据id进行删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result deleteById(String id) {
        try {
            interviewListService.deleteById(id);
            return new Result(true,"200",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"500",null);
        }
    }
}
