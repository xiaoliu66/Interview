package com.baidu.controller;

import com.baidu.entity.Result;
import com.baidu.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reportData")
public class ReportDataController {

    @Autowired
    private InterviewService interviewService;

    /**
     * 获取报表数据
     * @return
     */
    @RequestMapping("/getInterview")
    public Result getInterviewData() {
        try {
            Map<String,List<Object>> reportData = interviewService.getInterviewReportData();
            return new Result(true,"200",reportData);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"500",null);
        }
    }
}
