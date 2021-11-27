/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.netphenix.closeit.lite.model;

import java.util.Date;

/**
 *
 * @author desktop
 */
public class User {



    private Integer id;
    private String username;
    private String status;
    private String firstName;
    private String lastName;
    private String password;
    private Date lastResetTime;
    private Integer resetAttempt;   
    private Integer otp;
    private Date otpSentTime;
    private Date createdTime;
    private String hashCode;
    private Date loginTime;
    private Date lastOtpSentTime;
   // private boolean isAdim;
    private Integer resetOtp;
    private Integer loginAttempt;
    private short active;
    private Date lastLoginTime;
    private Date lastResetOtpSentTime;
    private String organization;
    private Integer organizationId;
    private Short sendMail;
    private Integer phone;
    private int isAdmin;
    
    


    
   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastResetTime() {
        return lastResetTime;
    }

    public void setLastResetTime(Date lastResetTime) {
        this.lastResetTime = lastResetTime;
    }

    public Integer getResetAttempt() {
        return resetAttempt;
    }

    public void setResetAttempt(Integer resetAttempt) {
        this.resetAttempt = resetAttempt;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Date getOtpSentTime() {
        return otpSentTime;
    }

    public void setOtpSentTime(Date otpSentTime) {
        this.otpSentTime = otpSentTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastOtpSentTime() {
        return lastOtpSentTime;
    }

    public void setLastOtpSentTime(Date lastOtpSentTime) {
        this.lastOtpSentTime = lastOtpSentTime;
    }

  /*  public boolean isIsAdim() {
        return isAdim;
    }

    public void setIsAdim(boolean isAdim) {
        this.isAdim = isAdim;
    }*/

    public Integer getResetOtp() {
        return resetOtp;
    }

    public void setResetOtp(Integer resetOtp) {
        this.resetOtp = resetOtp;
    }

    public Integer getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(Integer loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastResetOtpSentTime() {
        return lastResetOtpSentTime;
    }

    public void setLastResetOtpSentTime(Date lastResetOtpSentTime) {
        this.lastResetOtpSentTime = lastResetOtpSentTime;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization.getName();
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organization organization) {
        this.organizationId = organization.getId();
    }

    public Short getSendMail() {
        return sendMail;
    }

    public void setSendMail(Short sendMail) {
        this.sendMail = sendMail;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    
   
    
  
      
}
