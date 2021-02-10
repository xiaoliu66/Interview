package com.baidu.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TemplateService {

    List<String> getContext(MultipartFile excelFile);

}
