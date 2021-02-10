package com.baidu.service;

import java.util.List;
import java.util.Map;

public interface InterviewListService {
    Map<String, Object> getAllInterviewList(int currentPage, int pageSize);

    void deleteById(String id);
}
