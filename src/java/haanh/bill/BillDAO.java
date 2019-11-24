/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.bill;

import haanh.billdetail.BillDetailDTO;
import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class BillDAO {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public boolean insertBill(String userId, Timestamp time, double totalBill, List<BillDetailDTO> details, Map<Integer, Integer> mapQuantity) throws SQLException, NamingException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            con.setAutoCommit(false);

            String sqlInsertBill = "insert into Bill(UserId, Time, TotalBill) values (?,?,?)";
            stm = con.prepareStatement(sqlInsertBill);
            stm.setString(1, userId);
            stm.setTimestamp(2, time);
            stm.setDouble(3, totalBill);
            stm.executeUpdate();

            String sqlSelectBill = "select max(Id) as BillId from Bill where UserId=?";
            stm = con.prepareStatement(sqlSelectBill);
            stm.setString(1, userId);
            rs = stm.executeQuery();
            
            if (rs.next()) {
                int billId = rs.getInt("BillId");
                String sqlInsertDetail = "insert into BillDetail(BookId, Quantity, Price, BillId) "
                        + "values(?,?,?,?)";
                stm = con.prepareStatement(sqlInsertDetail);
                
                for (int i = 0; i < details.size(); i++) {
                    BillDetailDTO detail = details.get(i);
                    stm.setInt(1, detail.getBookId());
                    stm.setInt(2, detail.getQuantity());
                    stm.setDouble(3, detail.getPrice());
                    stm.setInt(4, billId);
                    
                    stm.executeUpdate();
                }
                
                String sqlUpdateQuantity = "update Book set Quantity=? where Id=? ";
                stm = con.prepareStatement(sqlUpdateQuantity);
                for (int i = 0; i < details.size(); i++) {
                    BillDetailDTO detail = details.get(i);
                    int inCartQuan = detail.getQuantity();
                    int dbQuan = mapQuantity.get(detail.getBookId());
                    int newQuan = dbQuan - inCartQuan;
                    stm.setInt(1, newQuan);
                    stm.setInt(2, detail.getBookId());
                    
                    stm.executeUpdate();
                }
                
                con.commit();
                result = true;
            }
        } catch (NamingException | SQLException e) {
            con.rollback();
            throw e;
        }  finally {
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
