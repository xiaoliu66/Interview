package com.baidu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Problem {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String context;
    private String mp3Name;
    private Double mp3Size;
    private Integer mp3Time;

    public Problem() {
    }

    public Problem(Integer id, String context, String mp3Name, Double mp3Size, Integer mp3Time) {
        this.id = id;
        this.context = context;
        this.mp3Name = mp3Name;
        this.mp3Size = mp3Size;
        this.mp3Time = mp3Time;
    }
}
