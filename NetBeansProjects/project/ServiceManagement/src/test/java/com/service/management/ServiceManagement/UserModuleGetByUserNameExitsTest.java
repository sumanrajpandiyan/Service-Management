/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.repository.UserRepository;
import com.netphenix.closeit.lite.service.UserService;
import java.util.HashMap;
import java.util.Map;
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
public class UserModuleGetByUserNameExitsTest {
    
    @Mock
   UserRepository repository;
    
   @InjectMocks
   UserService userService;

   User mockedUserObj;
   Map  assumption=new HashMap();
    @BeforeEach
    public void setUp(){

       assumption=new HashMap();
       MockitoAnnotations.initMocks(this);
       initDBMockUserObj();
       
     }   

   /**
     * Input User Name is null 
     * The Expected Message is "User Name does not exits"
     */
     @Test
    public void GetByUserNameExitsUserNameNull()throws Exception{
    
     Mockito.when(repository.getByUserNameExits("vasanth")).thenReturn(mockedUserObj);

         assumption.put("status", "new");
         assumption.put("message", "User Name does not exits");
           
     Assertions.assertEquals(assumption,userService.getByUserNameExits(null));
      } 

   /**
     *Input User Name is Empty 
     * The Expected Message is "User Name does not exits"
     */
     @Test
    public void GetByUserNameExitsUserNameNotGiven()throws Exception{
    
     Mockito.when(repository.getByUserNameExits("vasanth")).thenReturn(mockedUserObj);

    
         assumption.put("status", "new");
         assumption.put("message", "User Name does not exits");
           
     Assertions.assertEquals(assumption,userService.getByUserNameExits(""));
      } 

   /**
     * Database user Object is Active  
     * The Expected Message is "User Name already exits"
     */
     @Test
    public void GetByUserNameExitsUserIsActive()throws Exception{
            mockedUserObj.setActive((short)1);
     Mockito.when(repository.getByUserNameExits("vasanth")).thenReturn(mockedUserObj);

         assumption.put("status", "active");
         assumption.put("message", "User Name already exits");
           
     Assertions.assertEquals(assumption,userService.getByUserNameExits("vasanth"));
      } 

   /**
     * The Database user Object is InActive  
     * The Expected Message is "User name already exits do u want to active this user ? "
     */
     @Test
    public void GetByUserNameExitsUserInActive()throws Exception{
            mockedUserObj.setActive((short)0);
     Mockito.when(repository.getByUserNameExits("vasanth")).thenReturn(mockedUserObj);

         assumption.put("status", "inActive");
         assumption.put("message", "User name already exits do u want to active this user ? ");
         assumption.put("hashCode", mockedUserObj.getHashCode());
           
     Assertions.assertEquals(assumption,userService.getByUserNameExits("vasanth"));
      } 
   
  
   private void initDBMockUserObj(){
  
    mockedUserObj=new User();
    mockedUserObj.setUsername("vasanth");
    mockedUserObj.setActive((short)0);
    mockedUserObj.setHashCode("Zxas2528");
    
    
    
   
    }    
}
