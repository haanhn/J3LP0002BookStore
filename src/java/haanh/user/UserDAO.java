/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.user;

import haanh.utils.DBUtils;
import haanh.utils.StringUtils;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class UserDAO {
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    
    public UserDTO login(String userId, String password) throws NoSuchAlgorithmException, SQLException, NamingException {
        UserDTO dto = null;
        try {
            con = DBUtils.getConnection();
            String sql = "select RoleId from [User] "
                    + "where [User].UserId=? and [User].Password=? and [User].Active=?";
            
            String hashedPassword = StringUtils.getSHA256HashedString(password);
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, hashedPassword);
            stm.setBoolean(3, true);
            
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new UserDTO();
                dto.setUserId(userId);
                dto.setActive(true);
                dto.setRoleId(rs.getString("RoleId"));
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
