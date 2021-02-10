package com.baidu.service;

import com.baidu.entity.Interview;

import java.util.List;
import java.util.Map;

public interface InterviewService {
    void save(String startTime, String endTime, List<Map<String, String>> list);

    Map<String, List<Object>> getInterviewReportData();

    List<Interview> getAllInterview();

    Map<String,Object> getAllInterview(int currentPage, int pageSize);

    void deleteById(String id);
}
