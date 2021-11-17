/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.order.management.service;

import com.order.management.dao.CustomerDetails;
import com.order.management.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author desktop
 */
@Service
public class CustomerAPI {
  @Autowired
  CustomRepository repository; 
 
   public String addCustomer(CustomerDetails customer){
      
       if(!isvalidCustomer(customer))
        return "Please Enter Validate Customer Details";
         
       repository.save(customer);
         return "Success full Create customer";

     }
   

   public boolean isvalidCustomer(CustomerDetails customer){
      
      if(Utils.isEmpty(customer.getName()) ){
        return false;
       }
     
      if(Utils.isEmpty(customer.getAddress())){
        return false;
       }
  
      if(customer.getPhoneNumber()==0){
        return false;
        }
     
      return true;
     }

   public void getCustomer(int customerId){
      CustomerDetails costomer;
      costomer= repository.getById(customerId);

     } 
}

