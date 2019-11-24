/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.book.BookDTO;
import haanh.cart.CartUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaAnh
 */
public class ServletRemoveItemFromCart extends HttpServlet {
    
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
        
        String url = UrlConstants.PAGE_CART_DETAIL;
        
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            
            HttpSession session = request.getSession();
            Map<Integer, BookDTO> cart = (Map<Integer, BookDTO>) session.getAttribute(UrlConstants.ATTR_CART);
            
            CartUtils cartUtils = new CartUtils();
            cartUtils.removeBookFromCart(cart, bookId);
            
            session.setAttribute(UrlConstants.ATTR_CART, cart);
        } catch (Exception ex) {
            log(ex.getMessage(), ex);
            url = UrlConstants.PAGE_ERROR;
        }
        
        response.sendRedirect(url);
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

//    private String buildUrl(String url, String title, String min, String max, String category) {
//        url =  url + "?searchedTitle=" + title + "&minMoney=" + min + "&maxMoney=" + max + "&category=" + category;
//        return url;
//    }
}
