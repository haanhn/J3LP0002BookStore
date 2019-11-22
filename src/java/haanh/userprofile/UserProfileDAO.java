/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.userprofile;

import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class UserProfileDAO {
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    
    public UserProfileDTO findByUserId(String userId) throws NamingException, SQLException {
        UserProfileDTO dto = null;
        try {
            con = DBUtils.getConnection();
            String sql = "select Fullname, Email, Phone from UserProfile "
                    + "where UserProfile.UserId=?";
            
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new UserProfileDTO();
                dto.setUserId(userId);
                dto.setFullname(rs.getString("Fullname"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhone(rs.getString("Phone"));
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
    }
}
