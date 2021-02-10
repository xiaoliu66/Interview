package com.baidu.controller;

import com.baidu.entity.Interview;
import com.baidu.entity.Result;
import com.baidu.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/InterviewDetail")
public class InterviewDetailController {

    @Autowired
    private InterviewService interviewService;

    /**
     * 获取面试组信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result getInterviewDetail(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        try {
            Map<String,Object> allInterview = interviewService.getAllInterview(currentPage,pageSize);
            return new Result(true,"200",allInterview);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"500",null);
        }
    }

    /**
     * 删除面试组
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result deleteById(@RequestParam("id") String id) {
        try {
            interviewService.deleteById(id);
            return new Result(true,"200",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"500",null);
        }
    }
}
