/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.author;

import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class AuthorDAO {
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    
    public Map<Integer, String> getAllAuthors() throws SQLException, NamingException  {
        Map<Integer, String> map = new HashMap<>();
        try {
            String sql = "select Id, Name from Author";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("Id"), rs.getString("Name"));
            }
        } finally {
            closeConnection();
        }
        return map;
    }
    
    public boolean insertAuthor(AuthorDTO dto) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "insert into Author(Name) values (?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getName());
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
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
