package com.baidu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    // 注入数据源
    @Autowired
    private DataSource dataSource;


    // 配置对象 为了实现记住我功能
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    protected PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //释放静态资源
            web.ignoring().antMatchers("/css/**","/js/**","/bootstrap-3.3.7-dist/**","/html/**","/element-ui/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index.html").permitAll();

        // 配置无权限访问跳转到自定义页面
        http.exceptionHandling().accessDeniedPage("/403.html");



        http.formLogin()  // 自定义登录页面
                .loginPage("/index.html") //登录页面设置
                .loginProcessingUrl("/user/login")   // 登录访问路径
                .defaultSuccessUrl("/main.html").permitAll()  //登录成功后，跳转到的路径
                .and().authorizeRequests()
                .antMatchers("/index.html","/user/login").permitAll() //设置哪些路径可以直接访问 不需要认证
                // 当前登录用户，只有具有admins权限才可以访问这个路径
                //.antMatchers("/test/index").hasAuthority("admins")
                // 当前登录用户，只要有下面的权限中的一个就可以访问
                //.antMatchers("/test/index").hasAnyAuthority("admins,manager")
                //.antMatchers("/test/index").hasRole("sale")
                //.antMatchers("/test/index").hasAnyRole("")
                .anyRequest().authenticated()
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(3600 * 24 * 7)   // 设置有效时长 单位秒
                .and().userDetailsService(userDetailsService)
                .csrf().disable(); // 关闭csrf防护。
    }
}
