/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.bean.ResponseMessage;
import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.repository.UserRepository;
import com.netphenix.closeit.lite.service.EmailService;
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
public class UserModuleSendResetOtpTest {

   @Mock
   UserRepository repository;
    
   @InjectMocks
   UserService userService;

   @Mock 
   EmailService emailService;
   
   User mockedUserObj;
   User inputData;   
   
        @BeforeEach
        public void setUp(){

           MockitoAnnotations.initMocks(this);
           initDBUserObj();
           initUserInputData();
       }   
      
    /**
     * The Database user Object is null 
     * The Expected Message is "User not available"
     */
     @Test
    public void sentResetOTPUserObjNull()throws Exception{
    
     Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(null);

     String assumption="User not available"; 
     ResponseMessage message = userService.sendResetOTP(inputData);
     Assertions.assertSame(assumption,message.getMessage());
      } 
    
    /**
     * The Database user Object is Inactive  
     * The Expected Message is "User inactive"
     */
     @Test
    public void sentResetOTPUserInactive()throws Exception{
    
            mockedUserObj.setActive((short)0);
     Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);

     String assumption="User inactive"; 
     ResponseMessage message = userService.sendResetOTP(inputData);
     Assertions.assertSame(assumption,message.getMessage());
      } 
    
    /**
     * The Database user Object Status is locked  
     * The Expected Message is "Your account is locked"
     */
     @Test
    public void sentResetOTPUserStatusLocked()throws Exception{
    
            mockedUserObj.setStatus("locked");
     Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);

     String assumption="Your account is locked"; 
     ResponseMessage message = userService.sendResetOTP(inputData);
     Assertions.assertSame(assumption,message.getMessage());
      } 

    /**
     * The Database user Object Status is locked  
     * The Expected Message is "Your account is locked"
     */
 /*    @Test

    public void sentResetOTPLastResetOtpValidattion()throws Exception{
    
            String date="2021-03-28 19:50:40";
            mockedUserObj.setLastResetOtpSentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
     Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);
     //Mockito.when(utils::getSecondsFromTime(mockedUserObj.getLastResetOtpSentTime()).thenReturn(50);
 try(MockedStatic<userServiceUtils> theMock = Mockito.mockStatic(Utils.class)) {
     theMock.when(() -> Utils.getSecondsFromTime(mockedUserObj.getLastResetOtpSentTime())).thenReturn(50);

     String assumption="Remaining time for next otp is 40 seconds"; 
     ResponseMessage message = userService.sendResetOTP(inputData);
     Assertions.assertSame(assumption,message.getMessage());
      } 
} */
   /**
     * The Database user Object LastResetOtpSentTime is Null  
     * The Expected Message is "A one time password has been sent to your email address"
     */
      @Test

    public void sentResetOTPLastResetOtpSentTimeNull()throws Exception{
    
     Mockito.when(repository.getUserByUsesName("vasanth")).thenReturn(mockedUserObj);     
     Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);

     String assumption="A one time password has been sent to your email address"; 
     ResponseMessage message = userService.sendResetOTP(inputData);
     Assertions.assertSame(assumption,message.getMessage());
      } 
 

   private void initDBUserObj(){
  
    mockedUserObj=new User();
    mockedUserObj.setUsername("vasanth");
    mockedUserObj.setPassword("2580");
    mockedUserObj.setActive((short)1);
    mockedUserObj.setStatus(null);
    mockedUserObj.setLastOtpSentTime(null);
    mockedUserObj.setOtp(null);
    mockedUserObj.setLoginAttempt(null);
    mockedUserObj.setLastResetOtpSentTime(null);
    
   
    }

 private void initUserInputData(){
  
    //User Input Dat
    inputData=new User();
    inputData.setUsername("vasanth");
    inputData.setPassword("2580");
     

        }
}
