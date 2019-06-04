package com.wangxb.wxbhome.config;

import com.wangxb.wxbhome.authentication.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Security 主配置文件
 * @author wangxb
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) // 开启注解控制权限
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 注入 Security 属性类配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 配置TokenRepository
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 配置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        // 第一次启动的时候自动建表（可以不用这句话，自己手动建表，源码中有语句的）
//         jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 开启注解控制权限
     * 见Controller 中 @PreAuthorize("hasAuthority('XXX')")
     */
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题,默认前缀路径/resources/
        web.ignoring().antMatchers("/css/**","/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .and()
//                .rememberMe()
//                // 设置TokenRepository
//                .tokenRepository(persistentTokenRepository())
//                // 配置Cookie过期时间
////                .tokenValiditySeconds(securityProperties.getUser())
//                // 配置UserDetailsService
//                .userDetailsService(myUserDetailsService);


        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user-login")
                .defaultSuccessUrl("/index")
                .usernameParameter("nickname")
                .passwordParameter("password")
                .and()
                .authorizeRequests()// 表示以下都是授权的配置
                // /** 暂时解决Refused to apply style from 'http://localhost:8080/login' because its MIME type ('text/html') is not a supported stylesheet MIME type, and strict MIME checking is enabled.问题
                .antMatchers("/index","/join","/login","/password_reset").permitAll()

                .anyRequest()// 任何请求
                .authenticated();// 都需要身份认证

        http.logout()
                .logoutUrl("/user-logout")
                .logoutSuccessUrl("/login?logout");

    }
}
