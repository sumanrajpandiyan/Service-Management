/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.order.management.repository;

import com.order.management.dao.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author desktop
 */
public interface CustomRepository extends JpaRepository<CustomerDetails,Integer>{
    
}
