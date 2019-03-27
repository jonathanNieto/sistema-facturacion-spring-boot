package com.jonatnie.facturacionapp;


import com.jonatnie.facturacionapp.auth.handler.LoginSuccessHandler;
import com.jonatnie.facturacionapp.model.service.JpaUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * SpringSecurityConfig
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;
    
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder athManagerBuilder) throws Exception {
       athManagerBuilder.userDetailsService(userDetailsService)
       .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**","/img/**", "/js/**", "/webfonts/**", "/list", "/locale", "/api/**").permitAll()
        .anyRequest().authenticated()
        .and()
            .formLogin()
                .successHandler(successHandler)
                .loginPage("/login")
            .permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/error_403");
    }

    
    
}