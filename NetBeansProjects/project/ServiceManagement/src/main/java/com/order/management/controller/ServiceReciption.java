/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.order.management.controller;

import com.order.management.dao.Order;
import com.order.management.service.ServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author desktop
 */

@RestController
@RequestMapping("service")

public class ServiceReciption {
  @Autowired
  ServiceAPI serviceAPI;

 @PostMapping("/create")    
  public String createOrder(@RequestBody Order order){
     System.out.println("Order info received: "+order);
     return (serviceAPI.creatOrder(order));
     
     
 }

@PutMapping("/update")    
  public String updateOrder(@RequestBody Order order){
 
     System.out.println("working..");
     return "update success";
 }

@GetMapping("/getOrder")    
  public String getOrder(){
 
     System.out.println("working..");
     return "get success";
 }

}
