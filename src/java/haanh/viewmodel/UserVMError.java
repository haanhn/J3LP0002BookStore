/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.viewmodel;

import java.io.Serializable;

/**
 *
 * @author HaAnh
 */
public class UserVMError implements Serializable {
    
    private String userIdErr;
    private String oldPasswordErr;
    private String newPasswordErr;
    private String passwordErr;
    private String confirmErr;
    private String fullnameErr;
    private String emailErr;
    private String phoneErr;

    public UserVMError() {
    }

    public String getUserIdErr() {
        return userIdErr;
    }

    public void setUserIdErr(String userIdErr) {
        this.userIdErr = userIdErr;
    }

    public String getOldPasswordErr() {
        return oldPasswordErr;
    }

    public void setOldPasswordErr(String oldPasswordErr) {
        this.oldPasswordErr = oldPasswordErr;
    }

    public String getNewPasswordErr() {
        return newPasswordErr;
    }

    public void setNewPasswordErr(String newPasswordErr) {
        this.newPasswordErr = newPasswordErr;
    }

    public String getPasswordErr() {
        return passwordErr;
    }

    public void setPasswordErr(String passwordErr) {
        this.passwordErr = passwordErr;
    }

    public String getConfirmErr() {
        return confirmErr;
    }

    public void setConfirmErr(String confirmErr) {
        this.confirmErr = confirmErr;
    }

    public String getFullnameErr() {
        return fullnameErr;
    }

    public void setFullnameErr(String fullnameErr) {
        this.fullnameErr = fullnameErr;
    }

    public String getEmailErr() {
        return emailErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }

    public String getPhoneErr() {
        return phoneErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }
    
}
