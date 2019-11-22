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
public class UserVM implements Serializable {
    
    private String userId;
    private String fullname;
    private String email;
    private String phone;
    private Boolean active;
    private String roleId;

    public UserVM() {
    }

    public UserVM(String userId, String fullname, String email, String phone, Boolean active, String roleId) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.active = active;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
