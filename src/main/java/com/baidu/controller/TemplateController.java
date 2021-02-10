package com.baidu.controller;

import com.baidu.entity.Result;
import com.baidu.service.ProblemService;
import com.baidu.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * 模板excel文件的下载和上传
 */
@RestController
@RequestMapping("/upload")
public class TemplateController {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private ProblemService problemService;

    @PostMapping("/excel")
    public Result uploadExcel(@RequestParam("excelFile") MultipartFile excelFile) {
        // 读取上传的excel文件中内容
        List<String> context = templateService.getContext(excelFile);
        // 将所有数据存入到数据库中
        boolean flag = problemService.saveData(context);
        if (flag) {
            return new Result(true,"200","excel中的数据成功保存到数据库中");
        }else {
            return new Result(false,"400","excel中的数据保存到数据库中失败");
        }

    }

}
