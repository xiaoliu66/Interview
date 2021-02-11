package com.baidu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 有参构造
@NoArgsConstructor  // 无参构造
public class User {
    private Integer id;
    private String username;
    private String password;
    private String createtime;
}
