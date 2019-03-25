package com.jonatnie.facturacionapp.model.service;

import java.util.ArrayList;
import java.util.List;


import com.jonatnie.facturacionapp.model.dao.IUserDao;
import com.jonatnie.facturacionapp.model.entity.Role;
import com.jonatnie.facturacionapp.model.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JpaUserDetailsService
 */
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if (user == null) {
            logger.error("Error login: no existe usuario '" + username + "'");
            throw new UsernameNotFoundException("Username: '" + username + "' no exitse en el sistema");
        }
        for (Role role : user.getRoleList()) {
            logger.info("Role: " + role.getAuthority());
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        if (authorities.isEmpty()) {
            logger.error("Error login: usuario '" + username + "' no tiene roles asignados");
            throw new UsernameNotFoundException("Username: '" + username + "' no tiene roles asignados");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }

    
}