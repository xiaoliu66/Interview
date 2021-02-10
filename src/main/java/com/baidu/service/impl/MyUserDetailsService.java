package com.baidu.service.impl;

import com.baidu.entity.User;
import com.baidu.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 查询数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",s);
        User user = userMapper.selectOne(queryWrapper);

        // 判断
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        // 从数据库中查找 用户名对应的权限
        String role = userMapper.getRole(s);

        // "admins,ROLE_sale"
        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList(role);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),new BCryptPasswordEncoder().encode(user.getPassword()),auths);
    }
}
