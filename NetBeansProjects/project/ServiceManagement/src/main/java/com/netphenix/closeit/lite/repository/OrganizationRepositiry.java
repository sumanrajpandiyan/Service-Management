/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.netphenix.closeit.lite.repository;

import com.netphenix.closeit.lite.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author desktop
 */
public interface OrganizationRepositiry extends JpaRepository<Organization, String>{
    
}
