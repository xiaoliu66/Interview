package com.baidu.controller;

import com.baidu.entity.Interview;
import com.baidu.entity.Result;
import com.baidu.service.InterviewService;
import com.baidu.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Interview")
public class InterviewController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private InterviewService interviewService;


    /**
     * 随机抽取15道问题(采用语音)
     * @return
     */
    @GetMapping("/audio/{num}")
    public Result randoAudiomModel(@PathVariable int num) {
        List<Map<String,Object>> problemList = null;
        try {
            problemList = problemService.getRandomVoiceProblem(num);
            return new Result(true,"200",problemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"500",e);
        }
    }

    /**
     * 随机抽取15道问题
     * @return
     */
    @GetMapping("/{num}")
    public Result randomModel(@PathVariable("num") int num) {
        List<Map<String,Object>> problemList = null;
        try {
            problemList = problemService.getRandomProblem(num);
            return new Result(true,"200",problemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"500",problemList);

        }
        
    }

    /**
     * 存储面试结束相关信息
     * @param startTime
     * @param endTime
     * @param list
     * @return
     */
    @PostMapping("/finished")
    public Result finishedInterview(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime,@RequestBody List<Map<String,String>> list) {
        try {
            interviewService.save(startTime,endTime,list);
            return new Result(true,"200",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"500",null);
        }
    }
}
