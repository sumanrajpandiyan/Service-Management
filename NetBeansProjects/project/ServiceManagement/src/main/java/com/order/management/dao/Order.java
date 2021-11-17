/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.order.management.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author desktop
 */
@Entity
@Table(name="SERVICE_ORDER")
public class Order implements Serializable {
     
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private int orderId;

     private int customerId;
          
     @OneToMany(cascade={CascadeType.ALL},orphanRemoval = true,fetch =FetchType.LAZY)
     @JoinColumn(name="orderId",referencedColumnName = "orderId")
     List<Service> services;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    

   @Override
   public String toString() {
    final StringBuffer sb = new StringBuffer("Order{");
    sb.append("orderId=").append(orderId);
    sb.append(", services=").append(services);
     sb.append("customerId=").append(customerId);
    sb.append('}');
    return sb.toString();
  
}
}
 
 

