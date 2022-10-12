package com.example.facebookdemo.config;

import com.example.facebookdemo.service.CustomOAuth2UserService;
import com.example.facebookdemo.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    public WebSecurityConfig(UserServiceImpl userService, CustomOAuth2UserService customOAuth2UserService) {
        this.userService = userService;
        this.oauthUserService = customOAuth2UserService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/register","/forgot_password","/reset_password", "/message", "/login", "/callback", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
                .csrf().disable()
                .rememberMe()
                .rememberMeParameter("remember")
                .key("a31239e9-afbd-4a0f-9bac-43f532c12f55")
                .userDetailsService(userService)
                .rememberMeCookieName("rememberMe")
                .tokenValiditySeconds(10000)
                .and()
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login/oauth2")
                    .authorizationEndpoint(authorizationEndpoint ->
                authorizationEndpoint
                        .baseUri("/login/oauth2/authorization")
                    )
            );
    }
}
