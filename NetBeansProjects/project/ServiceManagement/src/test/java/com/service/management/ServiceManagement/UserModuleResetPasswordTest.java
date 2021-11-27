/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.bean.ResponseMessage;
import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.netphenix.closeit.lite.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.netphenix.closeit.lite.repository.UserRepository;
import org.mockito.InjectMocks;
import org.springframework.security.crypto.password.PasswordEncoder;



/**
 *
 * @author desktop
 */


public class UserModuleResetPasswordTest {
   @Mock
   UserRepository repository;
    
   @InjectMocks
   UserService userService;

    @Mock 
    EmailService emailService;

    @Mock
    PasswordEncoder encoder;

    User inputData = null;
    User mockedUserObj = null;
    @BeforeEach
    public void setup(){

          MockitoAnnotations.initMocks(this);       
          initDBMockUserObj();
          initUserInputData();
              
    }
     /**
      *User Name Not Enter In Input Data To Check User Name Equal to Null 
      */
     @Test
     public void resetPasswordInvalidUser() throws Exception{ 
           inputData.setUsername("");
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
      
       ResponseMessage message = userService.resetPassword(inputData, "http://localhost:8080/reset");
       String assumption="Invalid username";   
       Assertions.assertEquals(assumption,message.getMessage());
  }
     /**
      *URL Not Not Given Input Data To Check URL Equal to Null 
      */
     @Test
     public void resetPasswordURlNotAvailable() throws Exception{
        
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
      
       ResponseMessage message = userService.resetPassword(inputData, null);
       String assumption="Invalid username";   
       Assertions.assertEquals(assumption,message.getMessage());
  }  
     /**
      * User Name URl And OTP are Verify with Database And 
      * Given success and reset the password 
      */
     @Test
     public void resetPasswordSuccessTest() throws Exception{
        
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
 
       ResponseMessage message = userService.resetPassword(inputData, "http://localhost:8080/reset");
       String assumption="Password reset successfully and sent to your mail" ;   
       Assertions.assertEquals(assumption,message.getMessage());
  }

     /**
      * Reset OTP Not Available in Database and return OTP Not Available
      */  
     @Test
     public void resetPasswordOtpNotAvailableTest() throws Exception{
        mockedUserObj.setResetOtp(null);
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
                            
       ResponseMessage message = userService.resetPassword(inputData, "http://localhost:8080/reset");
       String assumption="OTP not available" ;   
       Assertions.assertEquals(assumption,message.getMessage());
  }
     /**
      * OTP Not Enter in input Data 
      */  
     @Test
     public void resetPasswordOtpNotEnterTest() throws Exception{
        
        inputData.setResetOtp(null);
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);       
              
       ResponseMessage message = userService.resetPassword(inputData, "http://localhost:8080/reset");
       String assumption="OTP is required" ;   
       Assertions.assertEquals(assumption,message.getMessage());
  }

     /**
      * Enter The Invalid OTP To user Input Data
      */ 
     @Test//OTP Invalid
     public void resetPasswordInvalidOTP() throws Exception{
        
        inputData.setResetOtp(8520);
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
      
       ResponseMessage message = userService.resetPassword(inputData, "http://localhost:8080/reset");
       String assumption="Enter valid OTP" ;   
       Assertions.assertEquals(assumption,message.getMessage());
  }

     /**
      * More Then 3 Times Attempt Due to Invalid OTP  The Account Status Locked
      */  
     @Test
     public void resetPasswordAccountLocked() throws Exception{
        
        mockedUserObj.setResetAttempt(5);
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
              
       ResponseMessage message = userService.resetPassword(inputData, "http://localhost:8080/reset");
       String assumption="Your account is locked" ;   
       Assertions.assertEquals(assumption,message.getMessage());
  }  
     /**
      * warring Message sent to mail Due to 3 times Invalid OTP Entered
      */  
     @Test
     public void resetPasswordAccountLockedSentMail() throws Exception{
        
       mockedUserObj.setResetAttempt(2);
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
              
       ResponseMessage message = userService.resetPassword(inputData, "http://localhost:8080/reset");
       String assumption="Your account is locked" ;   
       Assertions.assertEquals(assumption,message.getMessage());
  }
     @Test//Reset password sent Mail
     public void resetPasswordSentMail() throws Exception{
        mockedUserObj.setUsername(null);
       Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
              
       ResponseMessage message = userService.resetPassword(inputData, "http://localhost:8080/reset");
       String assumption="Password reset successfully and sent to your mail" ;   
       Assertions.assertEquals(assumption,message.getMessage());
  }

     private void initDBMockUserObj(){
           //DB Object        
        mockedUserObj=new User();
        mockedUserObj.setId(001);
        mockedUserObj.setOtp(2580);
        mockedUserObj.setResetAttempt(1);
        mockedUserObj.setUsername("vasanth");
        mockedUserObj.setResetOtp(2580);
       // mockedUserObj.setHashCode(null);
   
       }

     private void initUserInputData(){
  
        //User Input Dat
        inputData = new User();
        inputData.setId(001);
        inputData.setOtp(2580);
        inputData.setResetAttempt(1);
        inputData.setUsername("vasanth");
        inputData.setResetOtp(2580);     
       // inputData.setHashCode(null);     
       
        }
}
