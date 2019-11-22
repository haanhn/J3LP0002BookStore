/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.author.AuthorDAO;
import haanh.book.BookDAO;
import haanh.book.BookDTO;
import haanh.category.CategoryDAO;
import haanh.role.RoleDAO;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HaAnh
 */
public class ServletAdminViewBookDetail extends HttpServlet {

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
        
        log(this.getServletName() + " processRequest" );
        
        String url = UrlConstants.PAGE_ADMIN_BACKGROUND;
        request.setAttribute(UrlConstants.ATTR_INCLUDED_PAGE, UrlConstants.PAGE_ADMIN_BOOK_DETAIL);
        
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        
        try {
            BookDAO bookDAO = new BookDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            AuthorDAO authorDAO = new AuthorDAO();
            
            BookDTO bookDTO = bookDAO.getBookById(bookId);
            Map<Integer, String> authors = authorDAO.getAllAuthors();
            Map<Integer, String> categories = categoryDAO.getAllCategories();
            
            request.setAttribute(UrlConstants.ATTR_BOOK, bookDTO);
            request.setAttribute(UrlConstants.ATTR_AUTHORS, authors);
            request.setAttribute(UrlConstants.ATTR_CATEGORIES, categories);
        } catch (SQLException | NamingException e) {
            url = UrlConstants.PAGE_ERROR;
            log(e.getMessage(), e);
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

}
