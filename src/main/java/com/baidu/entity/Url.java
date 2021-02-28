package com.baidu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class Url {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String path;
    private String name;
    private Integer parentId;
    private String url;
    private String isParent;
    private String icon;
    private Integer priority;
    @TableField(exist = false)
    private List<Url> children;
}
