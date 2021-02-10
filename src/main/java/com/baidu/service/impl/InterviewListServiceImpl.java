package com.baidu.service.impl;

import com.baidu.entity.InterviewList;
import com.baidu.entity.TIntvIntvlist;
import com.baidu.mapper.InterviewListMapper;
import com.baidu.mapper.TIntvIntvlistMapper;
import com.baidu.service.InterviewListService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InterviewListServiceImpl implements InterviewListService {

    @Autowired
    private InterviewListMapper interviewListMapper;
    @Autowired
    private TIntvIntvlistMapper tIntvIntvlistMapper;

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getAllInterviewList(int currentPage, int pageSize) {
        Map<String, Object> map = new HashMap<>();

        Page<InterviewList> page = new Page<>(currentPage,pageSize);
        Page<InterviewList> listPage = interviewListMapper.selectPage(page, null);

        List<InterviewList> records = listPage.getRecords();

        map.put("currentPage",currentPage);
        map.put("pageSize",pageSize);
        map.put("total",listPage.getTotal());
        map.put("list",records);

        return map;
    }

    @Override
    public void deleteById(String id) {
        interviewListMapper.delete(new QueryWrapper<InterviewList>().eq("id",id));
        tIntvIntvlistMapper.delete(new QueryWrapper<TIntvIntvlist>().eq("interviewlist_id",id));
    }
}
