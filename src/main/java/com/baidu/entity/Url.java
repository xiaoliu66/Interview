package com.baidu.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Url {
    @TableId
    private Integer id;
    private String parentName;
    private Integer parentId;
    private String childrenTitle;
    private String childrenName;
    private String childrenUrl;
    private Character isParent;
}
