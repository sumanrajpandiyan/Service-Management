/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.order.management.controller;

import com.order.management.dao.CustomerDetails;
import com.order.management.service.CustomerAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author desktop
 */
//@Component
@RestController
@RequestMapping("user")
public class CustomerReciption {
  @Autowired
  CustomerAPI customerApi;
   
   @PostMapping("/sigin")
   public String createCustomer(@RequestBody CustomerDetails customer){
       System.out.println("Customer for sign :" +customer);
       customerApi.addCustomer(customer);
       return "Account Create for you";
    }
  
   @GetMapping("view")
   public String vieCustomer(@RequestBody CustomerDetails customer){
       System.out.println("Customer for sign :" +customer);
       customerApi.addCustomer(customer);
       return "Account Create for you";
    }
}
