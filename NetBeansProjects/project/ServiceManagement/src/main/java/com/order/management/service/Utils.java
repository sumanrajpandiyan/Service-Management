/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.order.management.service;

import java.util.Collection;

/**
 *
 * @author desktop
 */
public class Utils {

 public static boolean isEmpty(Collection<?> collection){
     return collection==null || collection.isEmpty();
  }

public static boolean isEmpty(String s){
     return s==null || s.isEmpty();
  }
    
}
