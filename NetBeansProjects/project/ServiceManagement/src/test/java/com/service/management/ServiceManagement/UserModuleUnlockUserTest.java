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
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author desktop
 */
public class UserModuleUnlockUserTest {

  @Mock
   UserRepository repository;
    
   @InjectMocks
   UserService userService;

   @Mock 
   EmailService emailService;
   
   @Mock
   PasswordEncoder encoder;

   User mockedUserObj = null;
   Optional<User> userOptional;
  
    @BeforeEach
    public void setup(){

          MockitoAnnotations.initMocks(this);       
          initDBMockUserObj();
          userOptional = Optional.of(mockedUserObj);
       }
 
   /**
     * hashcode is Not Available in Database so return ResponseMessage
     * Expected ResponseMessage "User not available"
     */
   @Test
   public void unlockUserHashCodeNotMatch()throws Exception{
       
       Mockito.when(repository.getUserByHashCode("sdf")).thenReturn(userOptional);
       
        String assumption="User not available";
        ResponseMessage message=userService.unlockUser("hashcode", "https://localhost:8080");
       Assertions.assertEquals(assumption,message.getMessage() );
   }

   /**
     * User Is InActive  so return ResponseMessage
     * Expected ResponseMessage "User inactive"
     */
   @Test
   public void unlockUserInactive()throws Exception{
            
        mockedUserObj.setActive((short)0);
       Mockito.when(repository.getUserByHashCode("hashcode")).thenReturn(userOptional);
       
        String assumption="User inactive";
        ResponseMessage message=userService.unlockUser("hashcode", "https://localhost:8080");
       Assertions.assertEquals(assumption,message.getMessage() );
   }

   /**
     * User Name is Null Not sent Mail and Not Save user then return ResponseMessage
     * Expected ResponseMessage "User unlocked successfully"
     */
   @Test
   public void unlockUserNameNull()throws Exception{
            
        mockedUserObj.setUsername(null);
       Mockito.when(repository.getUserByHashCode("hashcode")).thenReturn(userOptional);
       
        String assumption="User unlocked successfully";
        ResponseMessage message=userService.unlockUser("hashcode", "https://localhost:8080");
       Assertions.assertEquals(assumption,message.getMessage() );
   }

   /**
     * User URL is Null Not sent Mail and Not Save User then return ResponseMessage
     * Expected ResponseMessage "User unlocked successfully"
     */
   @Test
   public void unlockUserUrlNull()throws Exception{
            
        mockedUserObj.setUsername(null);
       Mockito.when(repository.getUserByHashCode("hashcode")).thenReturn(userOptional);
       
        String assumption="User unlocked successfully";
        ResponseMessage message=userService.unlockUser("hashcode",null);
       Assertions.assertEquals(assumption,message.getMessage() );
   }

   /**
     * save User and sent Mail success  and return ResponseMessage
     * Expected ResponseMessage "User unlocked successfully"
     */
   @Test
   public void unlockUser()throws Exception{
            
       Mockito.when(repository.getUserByHashCode("hashcode")).thenReturn(userOptional);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
    
        String assumption="User unlocked successfully";
        ResponseMessage message=userService.unlockUser("hashcode","https://localhost:8080");
       Assertions.assertEquals(assumption,message.getMessage() );
   }

    


   private void initDBMockUserObj(){
  
    mockedUserObj=new User();
    mockedUserObj.setUsername("vasanth");
    mockedUserObj.setPassword("2580");
    mockedUserObj.setActive((short)1);
    
    
    
   
    }

    
}
