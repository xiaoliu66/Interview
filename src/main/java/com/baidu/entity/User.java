package com.baidu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 有参构造
@NoArgsConstructor  // 无参构造
public class User {
    // 如果表的主键是自增长的，必须添加下面的注解。不然会报 argument type mismatch 参数不匹配错误。
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String createtime;


}
