package com.baidu.controller;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.entity.Problem;
import com.baidu.entity.Result;
import com.baidu.service.ProblemService;
import com.baidu.utils.BaiDuAIUtils;
import com.baidu.utils.StringToVoiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/voice")
public class GenerateVoiceFileController {

    @Autowired
    private ProblemService problemService;


    /**
     * 生成语音文件控制层
     * @return
     */
    @GetMapping("/generateVoiceFiles")
    public Result BaiduGenerateVoiceFiles() {
        // 获取ai语音生成对象
        AipSpeech client = BaiDuAIUtils.getAipSpeech();
        // 设置语音输出可选参数
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "4");
        options.put("pit", "5");
        options.put("per", "4");

        // 从数据库中查找表中的所有问题
        List<Problem> list = problemService.getAllContext();
        for (Problem problem : list) {
            String text = problem.getId() + "-" + problem.getContext();
            // 生成语音文件
            BaiDuAIUtils.generateVoiceFiles(client,options, text);

        }

        for (Problem problem : list) {
            String text = problem.getId() + "-" + problem.getContext();
            // 更新数据库中音频相关字段
            problemService.updateProblem(problem.getId(),text);
        }

        return new Result(true,"200","生成语音文件成功！");
    }

    /**
     *
     * @return
     */
    @GetMapping("/local")
    public Result LocalVoice() {
        // 从数据库中查找表中的所有问题
        List<Problem> list = problemService.getAllContext();
        for (Problem problem : list) {
            String text = problem.getId() + "-" + problem.getContext();
            // 生成语音文件
            StringToVoiceUtil.String2Voice(text);

        }
        return new Result(true,"200","成功生成语音文件！");
    }
}
