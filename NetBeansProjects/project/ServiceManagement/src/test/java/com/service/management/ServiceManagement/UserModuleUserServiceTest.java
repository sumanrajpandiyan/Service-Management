/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.repository.UserRepository;
import com.netphenix.closeit.lite.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author desktop
 */
public class UserModuleUserServiceTest {
   
   @Mock
   UserRepository repository;
    
  

   @Mock
   UserDetails userDetails;
  
   User mockedUserObj=null;

   Optional<User> userOptional;

   @InjectMocks
   UserService userService;
    
    List<User>users=new ArrayList<User>();
   
   @BeforeEach
    public void setup(){
          
          MockitoAnnotations.initMocks(this);       
          initDBUserObj();
          userOptional=Optional.of(mockedUserObj);
       }
 
    /**
     * This  Test for Save method
     * HashCode is NotNull save the Object 
     * Expected return User save object
     * HashCode is Null Generate the HashCode and save the Object 
     * Expected return User save object HashCode Not Null
     */
    @Test
    public void saveTest()throws Exception{        
         
        Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);

        Assertions.assertSame(mockedUserObj.getHashCode(), userService.save(mockedUserObj).getHashCode());
        mockedUserObj.setHashCode(null);
        Assertions.assertTrue(userService.save(mockedUserObj).getHashCode()!=null);
        
    }

   /**
     * This Test for Set And Save The User HashCode 
     * This No return Type
     * Expected result True
     */
    @Test
    public void setAndSaveHashcodeTest()throws Exception{
        
        mockedUserObj.setHashCode(null);
        Assertions.assertTrue(mockedUserObj.getHashCode()==null); 

        Mockito.when(repository.save(mockedUserObj)).thenReturn(mockedUserObj);
        userService.setAndSaveHashcode(mockedUserObj);

        Assertions.assertTrue(mockedUserObj.getHashCode()!=null);             
    }

     /**
     * This Test for Generate HashCode for HashCode Null User 
     * This No return Type
     * Expected result True
     */
    @Test
    public void generateHashCodeForUsersTest()throws Exception{
        
        mockedUserObj.setHashCode(null);
        Assertions.assertTrue(mockedUserObj.getHashCode()==null); 

        Mockito.when(repository.getUserWithoutHashCode()).thenReturn(users);
        userService.generateHashCodeForUsers();

        Assertions.assertTrue(mockedUserObj.getHashCode()!=null);             
    }
    
     /**
     * This Test for find The User by findById Input int id
     * Then return Optional collection 
     * Expected result True Or False
     */
    @Test
    public void getByIdTest()throws Exception{        
         
        Mockito.when(repository.findById(0025)).thenReturn(userOptional);
        Assertions.assertTrue(userService.getById(0025).isPresent());
        
        Mockito.when(repository.findById(0025)).thenReturn(userOptional);
        Assertions.assertFalse(userService.getById(123).isPresent());
       
    }
  
    
     /**
     * This Test for find The User by getByHashCode  Input String hashCode
     * Then return collection
     * Expected result True Or False
     */
    @Test
    public void getByHashCodeTest()throws Exception{        
         
        Mockito.when(repository.getUserByHashCode("25James")).thenReturn(userOptional);
        Assertions.assertTrue(userService.getByHashCode("25James").isPresent());
        
        Mockito.when(repository.getUserByHashCode("25James")).thenReturn(userOptional);
        Assertions.assertFalse(userService.getByHashCode("").isPresent());
       
    }
  
     /**
     * This Test for find The User by getByHashCode  Input String hashCode
     * Then return User Object Null and Not Null
     * Expected result True
     */
    @Test
    public void getUserByHashCodeTest()throws Exception{        
         
        Mockito.when(repository.getUserByHashCode("25James")).thenReturn(userOptional);
        Assertions.assertTrue(userService.getUserByHashCode("")==null);
        
        Mockito.when(repository.getUserByHashCode("25James")).thenReturn(userOptional);
        Assertions.assertTrue(userService.getUserByHashCode("25James")!=null);
       
    }
  
     /**
     * This Test for find The All User by findAll  
     * Then return List of User Object 
     */
    @Test
    public void getAllTest()throws Exception{        
         
        Mockito.when(repository.findAll()).thenReturn(users);
        Assertions.assertFalse(userService.getAll().isEmpty());
         
        users.clear();
        Mockito.when(repository.findAll()).thenReturn(users);
        Assertions.assertTrue(userService.getAll().isEmpty());
    }
  
     /**
     * This Test for To find The All User by findAll  
     * Then return List of User Object 
     */
    @Test
    public void getActiveUserTest()throws Exception{        
         
        Mockito.when(repository.getActiveUsers((short)1)).thenReturn(users);
        Assertions.assertFalse(userService.getActiveUser().isEmpty());
         
        users.clear();
        Mockito.when(repository.getActiveUsers((short)0)).thenReturn(users);
        Assertions.assertTrue(userService.getActiveUser().isEmpty());
    }
  
     /**
     * This Test for To get Active Mail sender 
     * Then return String Array of senders
     */
    @Test
    public void getActiveMailSenderTest()throws Exception{        
         
         String[] activeUsers={"user1","user2"};
         
        Mockito.when(repository.getActiveMailSender((short)1)).thenReturn(activeUsers);
        Assertions.assertEquals(activeUsers,userService.getActiveMailSender());
        
       }
  
     /**
     * This Test for To get Active Mail sender Users   
     * Then return List of User Object 
     */
    @Test
    public void getActiveMailSenderUserTest()throws Exception{        
         
        Mockito.when(repository.getActiveMailSenderUser((short)1)).thenReturn(users);
        Assertions.assertFalse(userService.getActiveMailSenderUser().isEmpty());
         
        users.clear();
        Mockito.when(repository.getActiveMailSenderUser((short)0)).thenReturn(users);
        Assertions.assertTrue(userService.getActiveMailSenderUser().isEmpty());
    }
  
     /**
     * This Test for To get Active Mail sender Users   
     * Then return List of User Object 
     */
  /*  @Test
    public void loadUserByUsernameTest()throws Exception{        

       Mockito.when(repository.findUserByUserName("vasanth")).thenReturn(mockedUserObj);
      // Mockito.when(UserPrinciple.build(mockedUserObj)).thenReturn(userDetails);
 
        try(MockedStatic<UserPrinciple> theMock = Mockito.mockStatic(UserPrinciple.class)) {
     theMock.when(() -> UserPrinciple.build(mockedUserObj)).thenReturn(userDetails);

       Assertions.assertEquals("vasanth",userService.loadUserByUsername("vasanth").getUsername());
         
    }
    }*/
  private void initDBUserObj(){
  
    mockedUserObj=new User();
    mockedUserObj.setId(0025);
    mockedUserObj.setUsername("james@java");
    mockedUserObj.setFirstName("James");
    mockedUserObj.setLastName("Gosling");
    mockedUserObj.setActive((short)1);
    mockedUserObj.setResetAttempt(1);
    mockedUserObj.setLastLoginTime(new Date());
    mockedUserObj.setLastOtpSentTime(null);
    mockedUserObj.setLoginAttempt(null);
    mockedUserObj.setResetAttempt(null);
    mockedUserObj.setStatus(null);
    mockedUserObj.setOtp(4534);   
    mockedUserObj.setHashCode("25James");   

    users.add(mockedUserObj);
    }

}
