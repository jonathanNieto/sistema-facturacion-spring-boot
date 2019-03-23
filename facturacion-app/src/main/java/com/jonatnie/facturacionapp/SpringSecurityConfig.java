package com.jonatnie.facturacionapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurityConfig
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder athManagerBuilder) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserBuilder userBuilder = User.builder().passwordEncoder(passwordEncoder::encode);

        athManagerBuilder.inMemoryAuthentication()
                .withUser(userBuilder.username("admin").password("123").roles("ADMIN", "USER"))
                .withUser(userBuilder.username("jonat").password("123").roles("USER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**","/img/**", "/js/**", "/webfonts/**", "/list").permitAll()
        .antMatchers("/detail/**").hasAnyRole("USER")
        .antMatchers("/upload/**").hasAnyRole("USER")
        .antMatchers("/form/**").hasAnyRole("ADMIN")
        .antMatchers("/delete/**").hasAnyRole("ADMIN")
        .antMatchers("/invoice/**").hasAnyRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin().permitAll()
        .and()
        .logout().permitAll();
    }

    
    
}