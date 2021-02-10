package com.baidu.service;

import com.baidu.entity.Problem;

import java.util.List;
import java.util.Map;

public interface ProblemService {
    boolean saveData(List<String> context);

    List<Problem> getAllContext();

    List<Map<String, Object>> getRandomVoiceProblem(int num);

    void updateProblem(Integer id, String text);

    List<Map<String,Object>> getRandomProblem(int num);
}
