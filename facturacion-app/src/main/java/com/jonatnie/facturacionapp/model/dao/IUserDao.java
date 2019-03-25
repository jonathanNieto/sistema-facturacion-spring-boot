package com.jonatnie.facturacionapp.model.dao;

import com.jonatnie.facturacionapp.model.entity.User;

import org.springframework.data.repository.CrudRepository;

/**
 * IUserDao
 */
public interface IUserDao extends CrudRepository<User, Long> {

    public User findByUsername(String username);

    /** another way to get the equvalent method 
     *  @Query("SELECT u FROM User u WHERE u.username = ?")
     *  public User fetchByUsername(String unsername);
     */
}