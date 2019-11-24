/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.bill.BillDAO;
import haanh.billdetail.BillDetailDTO;
import haanh.book.BookDAO;
import haanh.book.BookDTO;
import haanh.utils.DataUtils;
import haanh.utils.UrlConstants;
import haanh.viewmodel.UserVM;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaAnh
 */
public class ServletCheckOutOrder extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = UrlConstants.PAGE_LOGIN;
        
        UserVM currentUser = DataUtils.getCurrentUser(request);
        if (currentUser != null) {
            try {
                url = UrlConstants.PAGE_CART_DETAIL;
                
                HttpSession session = request.getSession();
                Map<Integer, BookDTO> cart = (Map<Integer, BookDTO>) session.getAttribute(UrlConstants.ATTR_CART);
                
                BookDAO bookDAO = new BookDAO();
                Map<Integer, Integer> mapQuantity = bookDAO.getMapBookWithQuantity(cart.keySet());
                
                Map<Integer, Integer> mapQuanErr = getMapQuantityError(cart, mapQuantity);
                if (mapQuanErr.isEmpty()) {
                    BillDAO billDAO = new BillDAO();
                    Timestamp time = new Timestamp(System.currentTimeMillis());
                    double totalBill = Double.parseDouble(request.getParameter("totalBill").trim());
                    boolean result = billDAO.insertBill(currentUser.getUserId(), time, totalBill, getListBillDetailsFromCart(cart), mapQuantity);
                    if (result) {
                        session.removeAttribute(UrlConstants.ATTR_CART);
                        request.setAttribute(UrlConstants.ATTR_MESSAGE, "Check out successfully!");
                    }
                } else {
                    request.setAttribute(UrlConstants.ATTR_ERROR_QUANTITY, mapQuanErr);
                    log("Error Quantity");
                }
            } catch (NamingException | SQLException ex) {
                log(ex.getMessage(), ex);
                url = UrlConstants.PAGE_ERROR;
            }
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<BillDetailDTO> getListBillDetailsFromCart(Map<Integer, BookDTO> cart) {
        List<BillDetailDTO> details = new ArrayList<>();
        
        Set<Map.Entry<Integer, BookDTO>> items = cart.entrySet();
        for (Map.Entry<Integer, BookDTO> item : items) {
            BookDTO book = item.getValue();
            
            BillDetailDTO detail = new BillDetailDTO();
            detail.setBookId(book.getId());
            detail.setQuantity(book.getQuantity());
            detail.setPrice(book.getPrice());
            
            details.add(detail);
        }
        return details;
    }
    
    private Map<Integer, Integer> getMapQuantityError(Map<Integer, BookDTO> cart, Map<Integer, Integer> mapQuantity) {
        Map<Integer, Integer> mapQuanErr = new HashMap<>();
        
        Set<Map.Entry<Integer, BookDTO>> items = cart.entrySet();
        for (Map.Entry<Integer, BookDTO> item : items) {
            int bookId = item.getKey();
            int inCartQuantity = item.getValue().getQuantity();
            int dbQuantity = mapQuantity.get(bookId);
            
            if (inCartQuantity > dbQuantity) {
                mapQuanErr.put(bookId, dbQuantity);
            }
            
            System.out.println("incart " + inCartQuantity);
            System.out.println("db " + dbQuantity);
        }
        return mapQuanErr;
    }
}
