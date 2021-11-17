/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.order.management.dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author desktop
 */
@Entity
@Table(name="SERVICE")
public class Service implements Serializable{
   
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int serviceId;
   private String productName;
   private int quntity;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuntity() {
        return quntity;
    }

    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }

   
     @Override
    public String toString() {
    final StringBuffer sb = new StringBuffer("Service{");
    sb.append("serviceId=").append(serviceId);
    sb.append(", productName='").append(productName).append('\'');
    sb.append(", quntity=").append(quntity);
    sb.append('}');
    return sb.toString();
}
   
}
