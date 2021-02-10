package com.baidu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data

public class Interview {
    @TableField("id") // @TableField用来解决实体类属性名和数据库字段名不一致的问题。
    private String id; // 本次面试的id

    private String name; // 本次面试的名称

    private LocalDateTime begin; // 面试开始的时间

    private LocalDateTime end; // 面试结束的时间

    private Integer time; // 本次面试的时长

    @TableField(exist = false) // 这里是用来解决，实体类的属性名在数据库中没有而导致报错的问题。查询时这个属性名就不会被写入sql语句中。
    private Map<String,String> problemMap; // 本次面试包含的题目和该题目回答的情况

    private Double rate; // 本次面试答题的正确率

}
