/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.netphenix.closeit.lite.bean;

import com.netphenix.closeit.lite.model.User;
import java.util.Map;

/**
 *
 * @author desktop
 */
public class ResponseMessage {

  private String status;
  private String message;

  User user;
  Map map;
    public ResponseMessage(String message, String status) {
        this.message=message;
        this.status=status;
        
    }

    public ResponseMessage(String status) {
        this.status=status;
    }

    public ResponseMessage(String userStatus, User user) {
        this.status=userStatus;
        this.user=user;
    }

    public ResponseMessage(String userStatus, Map map) {
        this.status=userStatus;
        this.map=map;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
