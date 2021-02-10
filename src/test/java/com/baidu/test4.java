package com.baidu;

import com.baidu.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test4 {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        String admin = userMapper.getRole("admin");
        System.out.println(admin);
    }
}
