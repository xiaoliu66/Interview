package com.baidu;

import com.baidu.entity.InterviewList;
import com.baidu.mapper.InterviewListMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class test2 {

    @Autowired
    private InterviewListMapper interviewListMapper;

    @Test
    public void testMybatis() {
        List<InterviewList> list = interviewListMapper.getAllList();
        System.out.println(list);

    }

    @Test
    public void testTime() {
        String time = interviewListMapper.getTime();
        System.out.println(time);
    }

    @Test
    public void test3() {
        for (int i = 1; i < 100; i++) {
            System.out.println(i);
        }
    }
}
