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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public List<BookDTO> getActiveBooks() throws NamingException, SQLException {
        List<BookDTO> list = new ArrayList<>();
        try {
            con = DBUtils.getConnection();
            String sql = "select Id, Title, Description, Quantity, Price, Image, DateImported, CategoryId, AuthorId from Book "
                    + "where Quantity > ? and Active=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, DBUtils.BOOK_QUANTITY_EMPTY);
            stm.setBoolean(2, true);

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
                dto.setActive(true);
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

    public List<BookDTO> searchBooks(String title, Double minMoney, Double maxMoney, Integer categoryId) throws NamingException, SQLException {
        List<BookDTO> list = new ArrayList<>();
        try {
            con = DBUtils.getConnection();
            
            String sql = "select Id, Title, Description, Quantity, Price, Image, CategoryId, AuthorId from Book ";
            sql = sql + getSearchQueryCondition(title, minMoney, maxMoney, categoryId);
            
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + title + "%");
            stm.setDouble(2, minMoney);
            stm.setDouble(3, maxMoney);
            stm.setBoolean(4, true);
            stm.setInt(5, DBUtils.BOOK_QUANTITY_EMPTY);
            if (categoryId != null && categoryId > 0) {
                stm.setInt(6, categoryId);
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                BookDTO dto = new BookDTO();
                dto.setId(rs.getInt("Id"));
                dto.setTitle(rs.getString("Title"));
                dto.setDescription(rs.getString("Description"));
                dto.setQuantity(rs.getInt("Quantity"));
                dto.setPrice(rs.getDouble("Price"));
                dto.setImage(rs.getString("Image"));
                dto.setActive(true);
                dto.setCategoryId(rs.getInt("CategoryId"));
                dto.setAuthorId(rs.getInt("AuthorId"));

                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public Map<Integer, Integer> getMapBookWithQuantity(Set<Integer> bookIds) throws NamingException, SQLException {
        Map<Integer, Integer> map = new HashMap<>();
        String sql = "select Id, Quantity from Book where Id ";
        sql = sql + getStringParamterInQuery(bookIds.size());
        
        try {
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            int i = 1;
            for (Integer id : bookIds) {
                stm.setInt(i, id);
                i++;
            }
            System.out.println("sql = " + sql);
            rs = stm.executeQuery();
            
            while (rs.next()) {
                map.put(rs.getInt("Id"), rs.getInt("Quantity"));
            }
        } finally {
            closeConnection();
        }
        return map;
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

    private String getSearchQueryCondition(String title, Double minMoney, Double maxMoney, Integer categoryId) {
        StringBuilder builder = new StringBuilder("");
        builder.append(" where Title like ? and Price >= ? and Price <= ? and Active=? and Quantity>? ");
        if (categoryId != null && categoryId > 0) {
            builder.append(" and CategoryId=? ");
        }
        return builder.toString();
    }
    
    private String getStringParamterInQuery(int totalParams) {
        StringBuilder s = new StringBuilder("");
        if (totalParams > 0) {
            s.append(" in (");
            boolean first = true;
            for (int i = 0; i < totalParams; i++) {
                if (first) {
                    s.append("?");
                    first = false;
                } else {
                    s.append(",?");     
                }
            }
            s.append(")");
        }
        return s.toString();
    }
}
