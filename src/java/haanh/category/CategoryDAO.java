/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.category;

import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class CategoryDAO {
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    
    public Map<Integer, String> getAllCategories() throws SQLException, NamingException  {
        Map<Integer, String> map = new HashMap<>();
        try {
            String sql = "select Id, Name from Category";
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
