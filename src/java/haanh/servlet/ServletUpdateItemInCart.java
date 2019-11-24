/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.book.BookDTO;
import haanh.book.BookError;
import haanh.cart.CartUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.util.Map;
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
public class ServletUpdateItemInCart extends HttpServlet {

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
            String quantityStr = request.getParameter("quantity").trim();
            BookError error = validateInput(quantityStr);
            if (error == null) {
                HttpSession session = request.getSession();
                Map<Integer, BookDTO> cart = (Map<Integer, BookDTO>) session.getAttribute(UrlConstants.ATTR_CART);
                BookDTO book = cart.get(bookId);
                
                int quan = Integer.parseInt(quantityStr);
                
                CartUtils cartUtils = new CartUtils();
                cartUtils.updateItemInCart(cart, book, quan);
                
                session.setAttribute(UrlConstants.ATTR_CART, cart);
            } else {
                request.setAttribute(UrlConstants.ATTR_ERROR, error);
            }
        } catch (Exception ex) {
            url = UrlConstants.PAGE_ERROR;
            log(ex.getMessage(), ex);
        }
        
        response.sendRedirect(url);
//        RequestDispatcher rd = request.getRequestDispatcher(url);
//        rd.forward(request, response);
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

    private BookError validateInput(String quantityStr) {
        boolean err = false;
        BookError error = new BookError();
        try {
            int quan = Integer.parseInt(quantityStr);
            if (quan <= 0) {
                err = true;
                error.setQuantityErr("Quantity > 0");
            }
        } catch (NumberFormatException e) {
            err = true;
            error.setQuantityErr("Integer format only");
        }
        
        if (!err) {
            error = null;
        }
        return error;
    }
}
