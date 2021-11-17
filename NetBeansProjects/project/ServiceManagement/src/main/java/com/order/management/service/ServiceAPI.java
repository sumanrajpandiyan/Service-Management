/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.order.management.service;

import com.order.management.dao.Order;
import com.order.management.dao.Service;
import com.order.management.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author desktop
 */
@org.springframework.stereotype.Service
public class ServiceAPI {
    @Autowired
    OrderRepository repository;
   
   public String creatOrder(Order order){

     if(!isValidOrder(order))
        return "Invalid inputs..";

     repository.save(order);    
     return "Order Created..";

   
   }

  public boolean isValidOrder(Order order){
     
   if(order.getCustomerId()==0){   
       return false;
    }
  
    if(Utils.isEmpty(order.getServices())){
     
       return false;
    }

    if(!isValidServices(order.getServices())){
      return false;
    }

   return true;
 }

  public boolean isValidServices(List<Service> services){
    
     for(Service service: services){
         if(Utils.isEmpty(service.getProductName())){
          return false;
        }

        if(service.getQuntity()==0){
          return false;
        }
     }
   return true;
  }

 
 }

