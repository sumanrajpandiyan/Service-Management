/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.management.ServiceManagement;

import com.order.management.dao.CustomerDetails;
import com.order.management.service.CustomerAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author desktop
 */

public class CustomerApiTest {
    
  CustomerDetails customer;
    CustomerAPI customerTest;

@BeforeEach
public void setUp(){

  customer=new CustomerDetails();
  customerTest =new CustomerAPI();
}
  @Test
  public void customerValidate(){
   
      customer.setName("Ram");
      customer.setAddress("Chidambaram");
      customer.setPhoneNumber(8344283490L);
      Assertions.assertTrue(customerTest.isvalidCustomer(customer));
   }

}
