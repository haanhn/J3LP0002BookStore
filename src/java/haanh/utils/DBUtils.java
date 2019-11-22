/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class DBUtils {
    
    public static final String ROLE_ADMIN = "AD001";
    
    public static Connection getConnection() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context env = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) env.lookup("J3LP0002BookStoreDS");
        Connection con = ds.getConnection();
        return con;
    }
}
