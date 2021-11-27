/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.netphenix.closeit.lite.service;

import com.netphenix.closeit.lite.model.Organization;
import com.netphenix.closeit.lite.repository.OrganizationRepositiry;
import jdk.jfr.internal.Repository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author desktop
 */
public class OrganizationService {
   Organization organization;
   @Autowired
   OrganizationRepositiry repository;
  
   public Organization getOrganizationByName(String organizationName) {
       organization=new Organization();
        System.out.println("getOrganizationByName");
        organization.setId(545);
       return organization;
    }

    public void addOrUpdate(Organization organization) {System.out.println("addorupdate");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        }
    
}
