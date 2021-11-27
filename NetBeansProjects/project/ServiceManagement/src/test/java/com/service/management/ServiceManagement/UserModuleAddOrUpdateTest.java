/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.repository.UserRepository;
import com.netphenix.closeit.lite.service.EmailService;
import com.netphenix.closeit.lite.service.UserDealRoleService;
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
public class UserModuleAddOrUpdateTest {

   @Mock
   UserRepository repository;
    
   @InjectMocks
   UserService userService;

   @Mock 
   EmailService emailService;
   
   @Mock
   PasswordEncoder encoder;

   @Mock
   UserDealRoleService userDealRoleService;

   User mockedUserObj = null;
   Optional<User> userOptional;
  
    @BeforeEach
    public void setup(){

          MockitoAnnotations.initMocks(this);       
          initDBMockUserObj();
          userOptional = Optional.of(mockedUserObj);
       }
 
   /**
     * UserId is Null,create user and save User
     * Sent The Mail to user
     */
   @Test
   public void addOrUpdateTest()throws Exception{
         mockedUserObj.setId(null);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
      
       Assertions.assertSame(mockedUserObj, userService.addOrUpdate(mockedUserObj, "https://localhost:8080"));
   }

    /**
     * UserId is Null And Username Not Given Case Creating User And save
     * Not Sent The Mail to user
     */
   @Test
   public void addOrUpdateTestUserNameNotGiven()throws Exception{
         mockedUserObj.setId(null);
         mockedUserObj.setUsername(null);

       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
       Mockito.doNothing().when(emailService).sendHTMLEmail(null, null, null, null, null);

       Assertions.assertSame(mockedUserObj, userService.addOrUpdate(mockedUserObj, "https://localhost:8080"));
   }

    /**
     * User And UserId Is Not Null but User Password Null
     * Repository can not find The user from Database
     */
   @Test
   public void addOrUpdateTestUserIdNotNull()throws Exception{

       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
       Mockito.when(repository.findById(005)).thenReturn(userOptional);
       Mockito.doNothing().when(userDealRoleService).deleteByUserHashCode(mockedUserObj.getId());

       Assertions.assertSame(mockedUserObj, userService.addOrUpdate(mockedUserObj, "https://localhost:8080"));
   }

    /**
     * User And UserId Is Not Null but User Password and Admin Null 
     * Repository can not find The user from Database
     */
   @Test
   public void addOrUpdateTestIsAdminNull()throws Exception{
              mockedUserObj.setIsAdmin(0);
       Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
       Mockito.when(repository.findById(005)).thenReturn(userOptional);
      // Mockito.doNothing().when(userDealRoleService).deleteByUserHashCode(mockedUserObj.getId());

       Assertions.assertSame(mockedUserObj, userService.addOrUpdate(mockedUserObj, "https://localhost:8080"));
   }

        
private void initDBMockUserObj(){
           //DB Object        
        mockedUserObj=new User();
        mockedUserObj.setId(005);
        mockedUserObj.setPassword(null);
        mockedUserObj.setFirstName("suman");
        mockedUserObj.setLastName("Raj");
        mockedUserObj.setUsername("vasanth");
        mockedUserObj.setIsAdmin(1);
        mockedUserObj.setActive((short)0);
        mockedUserObj.setHashCode(null);
          
       }

 }
