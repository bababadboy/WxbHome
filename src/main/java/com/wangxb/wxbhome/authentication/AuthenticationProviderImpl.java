package com.wangxb.wxbhome.authentication;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 认证提供者，校验用户，登录名密码，以及向系统提供一个用户信息的综合封装
 * @author wangxb
 */
@Slf4j
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    /**
     * 首先，在用户登录的时候，系统将用户输入的的用户名和密码封装成一个Authentication对象
     * 然后，根据用户名去数据源中查找用户的数据，这个数据是封装成的VUserDetails对象
     * 接着，将两个对象进行信息比对，如果密码正确，通过校验认证
     * 最后，将用户信息（含身份信息、细节信息、密码、权限等）封装成一个对象，此处参考UsernamePasswordAuthenticationToken
     * 最最后，会将这个对象交给系统SecurityContextHolder中（功能类似Session），以便后期随时取用
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info("AuthenticationProviderImpl authenticate login user [username={}, password={}]", username, password);

        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);
        log.info("AuthenticationProvider authenticate userDetails [userDetails={}]",userDetails);

        if(userDetails == null){
            throw new BadCredentialsException("用户没有找到");
        }

        if (!password.equals(userDetails.getPassword())) {
            log.info("AuthenticationProvider authenticate BadCredentialsException [inputPassword={}, DBPassword={}]",
                    password, userDetails.getPassword());
            throw new BadCredentialsException("密码错误");
        }

        //认证校验通过后，封装UsernamePasswordAuthenticationToken返回
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
