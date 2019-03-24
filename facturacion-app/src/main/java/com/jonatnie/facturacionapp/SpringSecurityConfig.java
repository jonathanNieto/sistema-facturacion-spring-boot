package com.jonatnie.facturacionapp;

import com.jonatnie.facturacionapp.auth.handler.LoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurityConfig
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;
    
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