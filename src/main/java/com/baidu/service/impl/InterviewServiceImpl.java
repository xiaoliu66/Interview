package com.baidu.service.impl;

import com.baidu.entity.Interview;
import com.baidu.entity.InterviewList;
import com.baidu.entity.TIntvIntvlist;
import com.baidu.mapper.InterviewListMapper;
import com.baidu.mapper.InterviewMapper;
import com.baidu.mapper.TIntvIntvlistMapper;
import com.baidu.service.InterviewService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewMapper interviewMapper;
    @Autowired
    private InterviewListMapper interviewListMapper;
    @Autowired
    private TIntvIntvlistMapper tIntvIntvlistMapper;

    /**
     * 保存面试结束相关信息
     * @param startTime
     * @param endTime
     * @param list
     */
    @Override
    public void save(String startTime, String endTime, List<Map<String, String>> list) {
        // 将相关面试信息存入interview表中
        Interview interview = new Interview();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime sTime = LocalDateTime.parse(startTime,formatter);
        LocalDateTime eTime = LocalDateTime.parse(endTime, formatter);

        Duration duration = Duration.between(sTime,eTime);
        long l1 = duration.toMillis() / 1000; // 单位 s

        // 计算平均答题率
        int totalKnowledge = 0;
        for (Map<String, String> map : list) {
            Integer value = Integer.valueOf(map.get("knowledge"));
            totalKnowledge += value;
        }

        double average = totalKnowledge / 15 * 1.0;

        interview.setId(startTime);
        interview.setName(startTime);
        interview.setBegin(sTime);
        interview.setEnd(eTime);
        interview.setTime((int) l1);
        interview.setRate(average);

        interviewMapper.insert(interview);

        // 将相关信息插入面试详情表中
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = list.get(i);
            InterviewList interviewList = new InterviewList();
            String s = startTime + "_" + (i+1);


            interviewList.setId(s);
            interviewList.setContext(map.get("context"));
            interviewList.setKnowledge(Double.valueOf(map.get("knowledge")));

            interviewListMapper.insert(interviewList);

            // 中间表
            TIntvIntvlist tIntvIntvlist = new TIntvIntvlist();
            tIntvIntvlist.setInterviewId(startTime);
            tIntvIntvlist.setInterviewlistId(s);

            tIntvIntvlistMapper.insert(tIntvIntvlist);
        }


    }

    /**
     * 获取报表数据
     * @return
     */
    @Override
    public Map<String, List<Object>> getInterviewReportData() {
        List<Interview> allInterview = getAllInterview();
        Map<String, List<Object>> listMap = new HashMap<>();

        List<Object> xAxisList = new ArrayList<>();
        List<Object> yAxisList = new ArrayList<>();

        for (Interview interview : allInterview) {
            xAxisList.add(interview.getId());
            yAxisList.add(interview.getRate());
        }

        listMap.put("xAxisList", xAxisList);
        listMap.put("yAxisList", yAxisList);
        return listMap;
    }

    /**
     * 获取表interview中的所有数据
     * @return
     */
    @Override
    public List<Interview> getAllInterview() {
        List<Interview> interviewList = interviewMapper.selectList(null);
        return interviewList;
    }

    /**
     * 获取表interview中的所有数据(分页)
     * @return
     * @param currentPage
     * @param pageSize
     */
    @Override
    public Map<String,Object> getAllInterview(int currentPage, int pageSize) {
        Map<String,Object> map = new HashMap<>();
        // 分页查询
        Page<Interview> page = new Page<>(currentPage,pageSize);
        Page<Interview> page1 = interviewMapper.selectPage(page, null);
        List<Interview> list = page1.getRecords();

        long current = page1.getCurrent();
        long size = page1.getSize();
        long total = page1.getTotal();

        map.put("list",list);
        map.put("currentPage",current);
        map.put("pageSize",size);
        map.put("total",total);

        return map;
    }

    /**
     * 根据id进行删除
     * @param id
     */
    @Override
    public void deleteById(String id) {
        interviewMapper.deleteById(id);
        interviewListMapper.delete(new QueryWrapper<InterviewList>().like("id",id));
        tIntvIntvlistMapper.delete(new QueryWrapper<TIntvIntvlist>().like("interview_id",id));
    }

}
