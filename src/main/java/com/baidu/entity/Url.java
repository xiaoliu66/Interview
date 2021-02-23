package com.baidu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class Url {
    @TableId
    private Integer id;
    private String path;
    private String name;
    private Integer parentId;
    private String url;
    private Character isParent;
    private String icon;
    private String priority;
    @TableField(exist = false)
    private List<Url> children;
}
