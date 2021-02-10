package com.baidu.service.impl;

import com.baidu.entity.Problem;
import com.baidu.mapper.ProblemMapper;
import com.baidu.service.ProblemService;
import com.baidu.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@Service
@Transactional
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    /**
     * 将excel中的数据保存到数据库中
     *
     * @param context
     * @return
     */
    @Override
    public boolean saveData(List<String> context) {
        try {
            for (String s : context) {
                /*
                    1.替换 / . ---> 空格
                    2.将英文?改为中文？
                 */
                if (s.contains("/") || s.contains(".") || s.contains("?")) {
                    String s1 = s.replace("/", "");
                    String s2 = s1.replace(".", "");
                    String s3 = s2.replace("?", "？");
                    Problem problem = new Problem();
                    problem.setId(null);
                    problem.setContext(s3);
                    problemMapper.insert(problem);
                } else {
                    Problem problem = new Problem();
                    problem.setId(null);
                    problem.setContext(s);
                    problemMapper.insert(problem);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return false;
        }
    }

    /**
     * 查询所有的Context字段内容
     *
     * @return
     */
    @Override
    public List<Problem> getAllContext() {
        List<Problem> problemList = problemMapper.selectList(null);
        return problemList;
    }

    /**
     * 随机抽取固定数量的题目(语音)
     *
     * @param num
     * @return
     */
    @Override
    public List<Map<String, Object>> getRandomVoiceProblem(int num) {
        List<Map<String, Object>> list = new ArrayList<>();
        Integer problemTotal = problemMapper.selectCount(null);

        Random r = new Random();
        Set<Integer> set = new HashSet();
        String prxUrl = "http://localhost:8080/data/";
        // 防止15个问题中有相同的
        while (set.size() < num) {
            Map<String, Object> map = new HashMap<>();
            int n = r.nextInt(problemTotal) + 1;
            if (set.contains(n)) {
                continue;
            }
            set.add(n);
            Problem problem = problemMapper.selectById(n);
            map.put("id", problem.getId());
            map.put("title", problem.getContext());
            map.put("src", prxUrl + problem.getId() + "-" + problem.getContext() + ".mp3");
            list.add(map);
        }
        return list;
    }

    /**
     * 更新problem的其他字段
     * @param id
     * @param text
     */
    @Override
    public void updateProblem(Integer id, String text) {
        File file = new File(FileUtils.path + text + ".mp3");
        // 获取文件的大小
        double size = FileUtils.getSize(file);
        // 获取文件的时长
        int time = FileUtils.getTime(file);
        problemMapper.updateById(new Problem(id,null,text + ".mp3",size,time));
    }

    /**
     * 随机抽取固定数量的题目
     * @param num
     * @return
     */
    @Override
    public List<Map<String, Object>> getRandomProblem(int num) {
        List<Map<String, Object>> list = new ArrayList<>();
        Integer problemTotal = problemMapper.selectCount(null);

        Random r = new Random();
        Set<Integer> set = new HashSet();
        // 防止15个问题中有相同的
        while (set.size() < num) {
            int n = r.nextInt(problemTotal) + 1;
            if (set.contains(n)) {
                continue;
            }
            Map<String,Object> map = new HashMap<>();
            set.add(n);
            Problem problem = problemMapper.selectById(n);
            map.put("id",problem.getId());
            map.put("context",problem.getContext());

            list.add(map);
        }
        return list;
    }
}
