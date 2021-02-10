package com.baidu.mapper;

import com.baidu.entity.InterviewList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface InterviewListMapper extends BaseMapper<InterviewList> {

    public List<InterviewList> getAllList();

    public String getTime();
}
