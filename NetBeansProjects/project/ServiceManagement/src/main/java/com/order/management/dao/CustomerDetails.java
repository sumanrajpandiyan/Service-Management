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
@Table(name="CUSTOMER")
public class CustomerDetails implements Serializable {
 

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private int customerId;
     
     private String name;
     
     private String address;
     
     private long phoneNumber;
   
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

@Override
public String toString() {
    final StringBuffer sb = new StringBuffer("CustomerDetails{");
    sb.append("customerId=").append(customerId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", address='").append(address).append('\'');
    sb.append(", phoneNumber=").append(phoneNumber);
    sb.append('}');
    return sb.toString();
}


    
}
