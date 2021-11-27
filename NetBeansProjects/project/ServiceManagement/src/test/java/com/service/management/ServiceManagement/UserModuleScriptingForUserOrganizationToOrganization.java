/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.model.Organization;
import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.repository.UserRepository;
import com.netphenix.closeit.lite.service.EmailService;
import com.netphenix.closeit.lite.service.OrganizationService;
import com.netphenix.closeit.lite.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class UserModuleScriptingForUserOrganizationToOrganization {

   @Mock
   UserRepository repository;
    
   @InjectMocks
   UserService userService;
   
    @Mock
    OrganizationService organizationService;   

    @Mock 
    EmailService emailService;

    @Mock
    PasswordEncoder encoder;

    @Mock
    Organization organizationObj;

    User inputData = null;
    User mockedUserObj = null;
   // Organization organizationObj;

    List<User>users=new ArrayList<>();
    @BeforeEach
    public void setup(){
          
          MockitoAnnotations.initMocks(this);       
          initDBMockUserObj();
          initUserInputData();
          initOrganization();
}
    @Test
    public void scriptingForUserOrganizationToOrganizationTest(){
        Map map = new HashMap();
         map.put("status", "Success");
          Mockito.when(repository.findAll()).thenReturn(users);
          Mockito.when(organizationService.getOrganizationByName("pandi")).thenReturn(organizationObj);
          //Mockito.doNothing().when(organizationService).addOrUpdate(organizationObj);
          
    
          Assertions.assertEquals(map,userService.scriptingForUserOrganizationToOrganization());
    }
     
private void initDBMockUserObj(){
           //DB Object        
        mockedUserObj=new User();
        mockedUserObj.setId(null);
        mockedUserObj.setResetAttempt(null);
        mockedUserObj.setUsername(null);
        mockedUserObj.setPassword(null);
        mockedUserObj.setActive((short)0);
        mockedUserObj.setFirstName(null);
        mockedUserObj.setLastName(null);
        mockedUserObj.setOrganization(organizationObj);
       
        users.add(mockedUserObj);
               
      }
private void initOrganization(){
         
       organizationObj =new Organization();
       organizationObj.setName("pandi");
      

}
private void initMap(){
 
  // map.put("status", "Success");
 }

private void initUserInputData(){
  
        //User Input Dat
        inputData = new User();
        inputData.setId(001);
        inputData.setFirstName("suman");
        inputData.setLastName("Raj");
        inputData.setUsername("vasanth");
        inputData.setResetOtp(2580);  
        inputData.setPassword("suman"); 
        inputData.setActive((short)0);  
       
        }
}
