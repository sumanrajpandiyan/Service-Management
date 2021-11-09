/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.order.management.service;

import com.order.management.dao.Order;
import com.order.management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author desktop
 */
@Service
public class ServiceAPI {
    @Autowired
    OrderRepository repository;

   public void creatOrder(Order order){
    repository.save(order);
}
 
}
