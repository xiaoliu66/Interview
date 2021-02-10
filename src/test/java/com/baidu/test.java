package com.baidu;

import com.baidu.mapper.ProblemMapper;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class test {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProblemMapper problemMapper;

    @Test
    public void testMysql() {
        String sql = "select * from student";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }
    }

    @Test
    public void testMybatisPlus() {

    }

    @Test
    public void testFile() {
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        File dir = h.getDir();
        String path =System.getProperty("user.dir")+"\\src\\main\\resources";

        System.out.println(path);
        System.out.println(jarF);
        System.out.println(dir);
        System.out.println(dir.getParentFile().toString());
    }

    @Test
    public void testChar() {
        StringBuilder s = new StringBuilder();
        String str = "/.?";
        String s1 = str.replace("/", "");
        String s2 = s1.replace(".", "");
        System.out.println(s2);
    }

    @Test
    public void test2() {
        StringBuilder s = new StringBuilder(".?");
        if (s.toString().contains(".")) {
            System.out.println("ok");
        }else {

        }
    }

    @Test
    public void testProblem() {
        Integer integer = problemMapper.selectCount(null);
        System.out.println(integer);
        Random r = new Random();
        for (int i = 0; i < 30; i++) {
            int i1 = r.nextInt(10 ) +1;
            System.out.println(i1);
        }

    }

    @Test
    public void testMusicFileSize() {
        File file = new File("D:\\IdeaProjects\\TestAI\\src\\main\\resources\\static\\data\\1-equals与==的区别 .mp3");
        Encoder encoder = new Encoder();
        long ls = 0;
        MultimediaInfo m;
        try {
            m = encoder.getInfo(file);
            ls = m.getDuration()/1000;

        } catch (Exception e) {
            System.out.println("获取音频时长有误：" + e.getMessage());
        }
        System.out.println("时长为：" + ls);
        System.out.println("大小为：" + file.length()/1024 * 1.0);
    }

    @Test
    public void testTime() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.parse("20210115220712",date);
        System.out.println(localDateTime);
    }
}
