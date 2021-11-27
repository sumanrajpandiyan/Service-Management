/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.repository.UserRepository;
import com.netphenix.closeit.lite.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author desktop
 */
public class UserModuleInvalidLoginAttemptTest {

    @Mock
   UserRepository repository;
    
   @InjectMocks
   UserService userService;
 
   User mockedUserObj=null;

   @BeforeEach
        public void setUp(){

           MockitoAnnotations.initMocks(this);
           initDBUserObj();
       }
 
/**
 * User Login Attempt is Null So the message save Null
 * The Expected return is Null
 */
@Test
public void UserModuleUserLoginAttemptNull()throws Exception {
   
     Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);

     Assertions.assertEquals(null,userService.invalidLoginAttempt(mockedUserObj));
     
  }

/**
 * User LoginAttempt =2  so increase one loginAttempt then userStatus=locked  
 * The Expected return message is "Attempt exceeded 3 times, account locked"
 */
@Test
public void UserModuleUserLoginAttemptEqualTwo()throws Exception {

      mockedUserObj.setLoginAttempt(2);
      String expMessage="Attempt exceeded 3 times, account locked";

     Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);

     Assertions.assertEquals(expMessage,userService.invalidLoginAttempt(mockedUserObj));
     
  }

/**
 * User LoginAttempt =1  so increase one loginAttempt  
 * The Expected return message is "Last 2 attempt failed next login attempt failure will lock the account"
 */
@Test
public void UserModuleUserLoginAttemptEqualOne()throws Exception {

      mockedUserObj.setLoginAttempt(1);
      String expMessage="Last 2 attempt failed next login attempt failure will lock the account";

     Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);

     Assertions.assertEquals(expMessage,userService.invalidLoginAttempt(mockedUserObj));
     
  }

  private void initDBUserObj(){
  
    mockedUserObj=new User();
    mockedUserObj.setLoginAttempt(null);
    mockedUserObj.setStatus(null);  

    }
}
