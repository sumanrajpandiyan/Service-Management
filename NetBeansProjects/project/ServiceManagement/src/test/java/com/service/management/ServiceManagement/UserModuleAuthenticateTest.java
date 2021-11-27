/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.repository.UserRepository;
import com.netphenix.closeit.lite.service.EmailService;
import com.netphenix.closeit.lite.service.UserService;
import java.util.Date;
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
public class UserModuleAuthenticateTest {
    
   @Mock
   UserRepository repository;
    
   @InjectMocks
   UserService userService;

   @Mock 
   EmailService emailService;
   
   User mockedUserObj=null;   
   
        @BeforeEach
        public void setUp(){

           MockitoAnnotations.initMocks(this);
           initDBUserObj();
       }    

      
    /**
     * Active Status is 0 case to return Null object is Expected
     * Repository can not find The user from Database
     */
     @Test
    public void authenticateTestActiveStatusNull()throws Exception{
    
     Mockito.when(repository.findUserByUserName("vasanth", (short)0)).thenReturn(mockedUserObj);
     Assertions.assertSame(null, userService.authenticate("vasanth", "authentication"));
      }    

    /**
     * Repository Find The User But User Is Null
     * User Null case to return Null object is Expected
     */
     @Test
    public void authenticateTestUserNull()throws Exception{
    
        mockedUserObj=null;
     Mockito.when(repository.findUserByUserName("vasanth", (short)0)).thenReturn(mockedUserObj);
     Assertions.assertSame(null, userService.authenticate("vasanth", "authentication"));
      }

   /**
     * User Name Not Available or Null case to return Null object is Expected
     * Repository can not find The user from Database
     */
     @Test
    public void authenticateTestUserNameNull()throws Exception{
    
     Mockito.when(repository.findUserByUserName("vasanth", (short)1)).thenReturn(mockedUserObj);
     Assertions.assertSame(null, userService.authenticate( null, "authentication"));
      }
    
    /**
     * Repository Find The User But ResetAttempt  Is Null From DB Object
     * save User object Is Null and return Save object is Expected
     * Not Sent Maill
     */
    @Test
    public void authenticateTestResetAttemptNull()throws Exception{
       
        mockedUserObj.setResetAttempt(null);
     Mockito.when(repository.findUserByUserName("vasanth", (short)1)).thenReturn(mockedUserObj);
     Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
        
     Assertions.assertSame(mockedUserObj,userService.authenticate( "vasanth", "authentication"));
      }

    /**
     * Repository Find The User But ResetAttempt  Is less then 3 From DB Object
     * save User object Is Null and return save object is Expected
     * Not Sent Maill
     */
    @Test
    public void authenticateTestResetAttemptlessThenThree()throws Exception{
       
        mockedUserObj.setResetAttempt(2);
     Mockito.when(repository.findUserByUserName("vasanth", (short)1)).thenReturn(mockedUserObj);
     Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
        
     Assertions.assertSame(mockedUserObj,userService.authenticate( "vasanth", "authentication"));
      }
    /**
     * Repository Find The User But ResetAttempt  Is less then 3 From DB Object
     * save User object Is Null and return save object is Expected
     * Sent Maill for Success Login
     */
    @Test
    public void authenticateTestSentMailForSuccessLogin()throws Exception{
       
        mockedUserObj.setResetAttempt(5);
     Mockito.when(repository.findUserByUserName("vasanth", (short)1)).thenReturn(mockedUserObj);
     Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
        
     Assertions.assertSame(mockedUserObj,userService.authenticate( "vasanth", "authentication"));
      }
    
  
private void initDBUserObj(){
  
    mockedUserObj=new User();
    mockedUserObj.setResetAttempt(1);
    mockedUserObj.setLastLoginTime(new Date());
    mockedUserObj.setLastOtpSentTime(null);
    mockedUserObj.setLoginAttempt(null);
    mockedUserObj.setResetAttempt(null);
    mockedUserObj.setStatus(null);
    mockedUserObj.setOtp(4534);   

    }

}
