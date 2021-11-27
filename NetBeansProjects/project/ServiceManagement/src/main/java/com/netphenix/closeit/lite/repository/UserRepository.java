/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.netphenix.closeit.lite.repository;

import com.netphenix.closeit.lite.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author desktop
 */
public interface UserRepository extends JpaRepository<User, Integer> {
   

   public List<User> getUserWithoutHashCode();

    public Optional<User> getUserByHashCode(String hashCode);

    public String[] getActiveMailSender(short activeStatus);

    public List<User> getActiveMailSenderUser(short activeStatus);

    public User findUserByUserName(String username, short activeStatus);

    public User findUserByUserName(String username);

    public User getUserByUsesName(String username, Integer id);

    public User getUserByUsesName(String username);

    public User findUserByUserNameAndOtp(String username, short activeStatus, Integer otp);

    public List<User> getResponsibleUserByOrganization(Integer orgId);

    public User getByUserNameExits(String userName);
    
    public User getActiveAndInActiveUser();
    
    public List<User> getActiveUsers(short c);

}
