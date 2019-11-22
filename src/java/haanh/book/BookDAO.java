/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.book;

import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class BookDAO {
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    
    public List<BookDTO> getAllBooks() throws NamingException, SQLException {
        List<BookDTO> list = new ArrayList<>();
        try {
            con = DBUtils.getConnection();
            String sql = "select Id, Title, Description, Quantity, Price, Image, DateImported, Active, CategoryId, AuthorId from Book";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            
            while (rs.next()) {
                BookDTO dto = new BookDTO();
                dto.setId(rs.getInt("Id"));
                dto.setTitle(rs.getString("Title"));
                dto.setDescription(rs.getString("Description"));
                dto.setQuantity(rs.getInt("Quantity"));
                dto.setPrice(rs.getDouble("Price"));
                dto.setImage(rs.getString("Image"));
                dto.setDateImported(rs.getDate("DateImported"));
                dto.setActive(rs.getBoolean("Active"));
                dto.setCategoryId(rs.getInt("CategoryId"));
                dto.setAuthorId(rs.getInt("AuthorId"));
                
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public BookDTO getBookById(int bookId) throws NamingException, SQLException {
        BookDTO dto = null;
        try {
            con = DBUtils.getConnection();
            String sql = "select Title, Description, Quantity, Price, Image, DateImported, Active, CategoryId, AuthorId from Book "
                    + "where Id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, bookId);
            rs = stm.executeQuery();
            
            if (rs.next()) {
                dto = new BookDTO();
                dto.setId(bookId);
                dto.setTitle(rs.getString("Title"));
                dto.setDescription(rs.getString("Description"));
                dto.setQuantity(rs.getInt("Quantity"));
                dto.setPrice(rs.getDouble("Price"));
                dto.setImage(rs.getString("Image"));
                dto.setDateImported(rs.getDate("DateImported"));
                dto.setActive(rs.getBoolean("Active"));
                dto.setCategoryId(rs.getInt("CategoryId"));
                dto.setAuthorId(rs.getInt("AuthorId"));
                
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean insertBook(BookDTO dto) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "insert into Book(Title, Description, Price, Quantity, Image, DateImported, Active, CategoryId, AuthorId) "
                    + "values(?,?,?,?,?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getTitle());
            stm.setString(2, dto.getDescription());
            stm.setDouble(3, dto.getPrice());
            stm.setInt(4, dto.getQuantity());
            stm.setString(5, dto.getImage());
            stm.setDate(6, dto.getDateImported());
            stm.setBoolean(7, true);
            stm.setInt(8, dto.getCategoryId());
            stm.setInt(9, dto.getAuthorId());
            
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean updateBook(BookDTO dto) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "update Book set Title=?, Description=?, Price=?, Quantity=?, DateImported=?, "
                    + "Active=?, CategoryId=?, AuthorId=? "
                    + "where Id=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getTitle());
            stm.setString(2, dto.getDescription());
            stm.setDouble(3, dto.getPrice());
            stm.setInt(4, dto.getQuantity());
            stm.setDate(5, dto.getDateImported());
            stm.setBoolean(6, dto.getActive());
            stm.setInt(7, dto.getCategoryId());
            stm.setInt(8, dto.getAuthorId());
            stm.setInt(9, dto.getId());
            
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean updateBookImage(int bookId, String image) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "update Book set Image=? "
                    + "where Id=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, image);
            stm.setInt(2, bookId);
            
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean deleteBookById(int bookId) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "update Book set Active=? where Id=?";
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, false);
            stm.setInt(2, bookId);
            
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
