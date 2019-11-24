/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.user;

import java.io.Serializable;

/**
 *
 * @author HaAnh
 */
public class UserDTO implements Serializable {
    
    private String userId;
    private String password;
//    private String fullname;
//    private String email;
//    private String phone;
//    private String photo;
    private Boolean active;
    private String roleId;

    public UserDTO() {
    }

    public UserDTO(String userId, String password, Boolean active, String roleId) {
        this.userId = userId;
        this.password = password;
        this.active = active;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
