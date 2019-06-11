package com.wangxb.wxbhome.config;

import com.wangxb.wxbhome.authentication.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
        //解决静态资源被拦截的问题,默认前缀路径/resources

        web.ignoring().antMatchers("/static/**");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user-login")
                .defaultSuccessUrl("/index")
                .usernameParameter("nickname")
                .passwordParameter("password")
                .and()
                .authorizeRequests()// 表示以下都是授权的配置
                .antMatchers("/","/index","/join","/login","/user-signup","/password_reset","/kaptcha","/hello","verify_code").permitAll()
                .anyRequest()// 任何请求
                .authenticated()
                .and()
                .rememberMe()
                // 设置TokenRepository
                .tokenRepository(persistentTokenRepository())
                //记住我秒数
                .tokenValiditySeconds(60*60*24*7)
                // 配置UserDetailsService
                .userDetailsService(userDetailsService);;// 都需要身份认证


        http.logout()
                .logoutUrl("/user-logout")
                .logoutSuccessUrl("/login?logout");

    }
}
