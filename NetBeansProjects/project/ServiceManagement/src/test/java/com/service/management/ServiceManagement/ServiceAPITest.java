/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.order.management.dao.Order;
import com.order.management.dao.Service;
import com.order.management.service.ServiceAPI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 *
 * @author desktop
 */

public class ServiceAPITest {


ServiceAPI serviceTest;
Service service; 
List<Service> services;
Order order;
/*@BeforeAll
public static void init(){
  System.out.println("unit Test Initate for service order API");
}*/

@BeforeEach
public void setup(){
 order =new Order();
 serviceTest=new ServiceAPI();
 service =new Service();
 services=new ArrayList<>();
 }  
@Test   
public void serviceOrderValidCaseTest(){
    
      service.setProductName("Nokia");
      service.setQuntity(5);
      services.add(service);
      order.setCustomerId(001);
      order.setServices(services);
     Assertions.assertTrue(serviceTest.isValidOrder(order)) ;
     
   
   }
 @Test    
public void serviceValidCaseTest(){
    
     service.setProductName("Samsung");
     service.setQuntity(5);
     services.add(service);

    Assertions.assertTrue(serviceTest.isValidServices(services));

   }


@Test
public void serviceOrderInvalidCaseTest(){
   
  //All value Not given Case
     service.setProductName("");
      service.setQuntity(0);
      services.add(service);
      order.setCustomerId(0);
      order.setServices(services);
     Assertions.assertFalse(serviceTest.isValidOrder(order)) ;

    
     }
    

@Test
 public void serviceInvalidCaseTest(){
  
  //All service value Are Not Given Case 
     service.setProductName("");
     service.setQuntity(0);
     services.add(service);
    Assertions.assertFalse(serviceTest.isValidServices(services));
 
   }    
}
