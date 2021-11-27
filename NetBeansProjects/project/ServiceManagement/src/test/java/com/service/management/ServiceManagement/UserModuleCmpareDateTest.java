/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.netphenix.closeit.lite.service.UserService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author desktop
 */
public class UserModuleCmpareDateTest {
    
    UserService userService;
     
    Date lastResetDate; 
    Integer resetAttempt;
    String date;

    @BeforeEach 
    public void setUp() throws ParseException{
 
      date="28/03/2021";
      userService=new UserService();
      lastResetDate= new SimpleDateFormat("dd/MM/yyyy").parse(date); 
      resetAttempt=1;
  } 
    /**
     * Last Reset Date and today Date is Not Same 
     * This case to return resetAttempt=Null is Expected
     */
    @Test
    public void compareDateValidPara(){

      Assertions.assertEquals(null,userService.compareDate(lastResetDate,resetAttempt));
     }

    /**
     * Last Reset Date null and resetAttempt=1 
     * This case to return resetAttempt=1 is Expected
     */
    @Test
    public void compareDateLastResetDateNull(){

         lastResetDate=null;
      Assertions.assertEquals(1,userService.compareDate(lastResetDate,resetAttempt));
     }

    /**
     * Last Reset Date null and resetAttempt=1 
     * This case to return resetAttempt=1 is Expected
     */
    @Test
    public void compareDateLastResetDateIsTodayDate(){
 
         lastResetDate= new Date();
      Assertions.assertEquals(1,userService.compareDate(lastResetDate,resetAttempt));
     }
}
